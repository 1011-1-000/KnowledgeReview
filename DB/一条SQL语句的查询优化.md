### 一条SQL语句在PostgreSQL中的查询优化过程

这篇文章根据在公司的优化过程整理而来，由于公司的安全这一块比较重视，所以很多相关的测试数据不能准确的在这里给出，但依然可以做为参考。并对其中一些术语或者相关技术做一些解释。

这里不再废话，简单介绍一下这条SQL及表的情况，表中数据1100W左右，SQL语句为
```sql
SELECT column FROM table WHERE date >= '2018-01-01' AND date <= '2018-01-31' AND type = 'PORT' GROUP BY column
```
查询耗时：15.2s 左右

#### 创建索引

拿到这条SQL的第一件事Explain查看执行计划。没有建索引。
```sql
CREATE INDEX date_type_idx ON table (date, type);
```
索引建立后，查询速度提升到2.4 - 2.5s

#### 创建聚集索引

简单的建立索引后并没有达到我们预期的效果，因此尝试继续优化。简单的索引，会导致一个随机读写的问题，而随机读写的运算代价在Postgresql中是顺序读写的4倍，相对来说还是比较高的，而数据库也在这一方面做了一些优化，比如通过把这种简单索引转化位图索引，再进行查询，大大提高了查询效率。实际上我们在做完优化后通过Explain也可以看到执行计划是使用了位图索引，但这仍然没能解决问题。从随机读写及位图索引的角度出发，我们考虑把这个索引做成聚集索引，通过把这些数据放到连续的物理地址存储来进一步提高性能。
```sql
CLUSTER date_type_idx ON table
```
SQL的查询降了1s左右。

tips:
- 聚集索引：表中各行的物理顺序与键值的逻辑（索引）顺序相同，表中只能包含一个聚集索引。
- 非聚集索引：表中各行的物理顺序与键值的逻辑（索引）顺序不匹配

#### Postgresql并行查询特性

通过聚集索引的方式，我们把查询性能降到了1s以内，但是从我们的角度来说，我们想知道我们还能优化多少，所以我们又尝试把这个数据导入SQLserver,按照同样的方式做了索引，再次执行，结果令人咂舌，只要100ms左右。通过这样的比较，至少我们有了一个优化的基准，为什么SQLserver可以做到100ms左右给出结果，而Postgresql不可以，在检查SQLserver的执行情况时我们发现SQLserver的query是走的并行查询,同时可以明显的观察到CPU(Hash及Sort)及Memory的变化.而在使用Postgresql却并没有使用这些资源。因此，这里我们开始考虑为什么Postgresql不能做并行？

答案其实很简单，我们一直使用的版本是9.5，而在9.6之前，Postgresql并不支持并行查询特性，因此我们把数据库升级到最新的稳定版本10.4,查询时间降低到350ms左右，是一个不错的性能提升，这期间我们也做了一些数据库的配置工作，执行Explain可以看到workers planned: 2,以及Parallel scan.

简单介绍一些常用的参数配置：
- max_worker_processes: 设置系统支持最大后台进程数。默认数值为8.
- max_parallel_workers_per_gather：设置每条Query执行时可以使用的最大进程数。它的个数受限于max_worker_processes.默认值为2.
- dynamic_shared_memory_type: 这一项需要根据系统配置，而不能设置为None，因为并行查询需要在多个进程之间共享数据。

这是关于Postgresql并行查询设置的一些参数，其中workers planned貌似很难调大，它会根据你表的大小自动选择最优的进程个数。
详细的参数配置请参照：[Postgresql服务器配置](https://www.postgresql.org/docs/10/static/runtime-config.html)

#### Index Only Scan方式

经过上一步的操作，SQL的查询性能已经好了很多，在date范围增大时也不会大幅的降低性能，但350ms与100ms还有有个250ms的差距，为了进一步提高性能，我们又从索引的角度来考虑性能提升，为什么会从这里出发，因为实际上这个GROUP BY最后返回的数据仅仅只有12条，而为了这12条数据，我们需要遍历在2018-01-01与2018-01-31之间77W的全部数据，感觉不是很值得，因此，我们考虑为什么不能把这一部分数据完全建成索引，而让我们的Query只需要扫描索引就可以得到这些数据。这里我们重新建立索引：

第一次建立索引如下：
```sql
CREATE INDEX column_date_type_idx ON table (column,date, type);
```
Explain发现可以走index only scan，但是性能提升并不明显。第一感觉是这种索引顺序不适合我们的这条Query.调整索引顺序：
```sql
CREATE INDEX column_date_type_idx ON table (date, type，column);
```
查询耗时：140ms。
到此，我们的SQL性能优化告一段落。整体来看，还是有很多杂乱的知识点，通过这些也对数据库底层的逻辑有了进一步的了解，算是一次不错的调优实践吧。

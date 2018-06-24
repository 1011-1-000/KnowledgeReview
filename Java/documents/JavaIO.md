#### JavaIO中的类图结构

```
├── JavaIO
    ├── 字节流
    │ ├── InputStream
    │ │ ├── FileInputStream
    │ │ ├── FilterInputStream
    │ │ │ ├── BufferedInputStream
    │ │ │ ├── DataInputStream
    │ │ │ ├── PushbakInputStream
    │ │ ├── ObjectInputStream
    │ │ ├── PipedInputStream
    │ │ ├── SequenceInputStream
    │ │ ├── StringBufferInputStream
    │ │ ├── ByteArrayInputStream
    │ ├── OutputStream
    │ │ ├── FileOutputStream
    │ │ ├── FilterOutputStream
    │ │ │ ├── BufferedOutputStream
    │ │ │ ├── DataOutputStream
    │ │ │ ├── PrintStream
    │ │ ├── ObjectOutputStream
    │ │ ├── PipedOutputStream
    │ │ ├── ByteArrayOutputStream
    ├── 字符流
    │ ├── Reader
    │ │ ├── BufferedReader
    │ │ ├── InputStreamReader
    │ │ │ ├── FileReader
    │ │ ├── StringReader
    │ │ ├── PipedReader
    │ │ ├── ByteArrayReader
    │ │ ├── FilterReader
    │ │ │ ├── PushbackReader
    │ ├── Writer
    │ │ ├── BufferedWriter
    │ │ ├── OutputStreamWriter
    │ │ │ ├── FileWriter
    │ │ ├── PrinterWriter
    │ │ ├── StringWriter
    │ │ ├── PipedWriter
    │ │ ├── CharArrayWriter
    │ │ ├── FilterWriter
```

#### 流的分类
流的本质是数据的传输。

从上面的类图结构也可以清楚的看到，从数据的处理类型来看，可以分为：
- 字符流
- 字节流

字节流与字符流的区别：
- 字节流操作的基本单元为字节；字符流操作的基本单元为Unicode码元。
- 字节流默认不使用缓冲区；字符流使用缓冲区。
- 字节流通常用于处理二进制数据，实际上它可以处理任意类型的数据，但它不支持直接写入或读取Unicode码元；
- 字符流通常处理文本数据，它支持写入及读取Unicode码元

从数据的流向来看，可以分为：
- 输入流
- 输出流
这里的输入与输了都是针对内存来说的

从类的功能来看，大致可以分为：

- 节点流：直接与数据源相连，读入或写出。
- 处理流：处理流与节点流通常一块使用，处理流初始化通常以节点流做为参数。

常用的节点流：

- 基类：InputStream, OutputStream, Reader, Writer
- 文件：FileInputStream, FileOutputStream, FileReader, FileWriter
- 数组：ByteArrayInputStream, ByteArrayOutputStream, CharArrayReader, CharArrayWriter
- 字符串：StringReader, StringWriter
- 管道：PipedInputStream, PipedOutputStream, PipedReader, PipedWriter

常用的处理流：
- 缓冲流：BufferedInputStream, BufferedOutputStream, BufferedReader, BufferedWriter
- 转换流：InputStreamReader, OutputStreamWriter
- 数据流：DataInputStream, DataOutputStream

#### 示例代码
更多关于管道和缓冲代码，请参照[这里]()。
```java
/*
 * Copyright [2018] [Leo].
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * @author Leo
 * @since 2018/6/23 下午10:58
 * @email leo.anonymous@qq.com
 */

import java.io.*;

public class JavaIO {

    public static void writeToFileByByte(String path) throws IOException {

        String str = "write the string to the file by the byte";
        File file = new File(path + "/writeByByte.txt");

        FileOutputStream os = new FileOutputStream(file);
        os.write(str.getBytes());
        os.close();

    }

    public static void readFromFileByByte(String path) throws IOException {

        File file = new File(path + "/writeByByte.txt");
        FileInputStream is = new FileInputStream(file);
        byte[] byteArray = new byte[(int) file.length()];
        is.read(byteArray);
        is.close();

        System.out.println(new String(byteArray));
    }

    public static void writeToFileByChar(String path) throws IOException {

        String str = "write the string to the file";
        File file = new File(path + "/writeToFile.txt");
        Writer writer = new FileWriter(file);

        writer.write(str);
        writer.close();
    }

    public static void readFromFileByChar(String path) throws IOException {

        File file = new File(path + "/writeToFile.txt");
        Reader reader = new FileReader(file);
        char[] byteArray = new char[(int)(file.length())];
        reader.read(byteArray);
        reader.close();
        System.out.println(byteArray);
    }

    public static void convertByteToChar(String path) throws IOException {

        File file = new File(path + "/writeByByte.txt");

        InputStream is = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(is);

        char[] charArray = new char[(int)file.length()];
        isr.read(charArray);
        isr.close();
        System.out.println(charArray);
    }

    public static void main(String[] args) throws IOException {
        String path = "/Users/leo/Documents/study/KnowledgeReview/Java/resources/io";
        writeToFileByByte(path);
        readFromFileByByte(path);
        writeToFileByChar(path);
        readFromFileByChar(path);
        convertByteToChar(path);
    }
}

```

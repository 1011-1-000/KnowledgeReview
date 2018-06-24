package main.com.leo.io.pipe;

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
 * @since 2018/6/24 上午9:43
 * @email leo.anonymous@qq.com
 */

import java.io.IOException;
import java.io.PipedInputStream;

public class PipeInput implements Runnable {

    public PipedInputStream input;

    public PipeInput(PipedInputStream input) {
        this.input = input;
    }

    @Override
    public void run() {
        try {
            int data = input.read();
            while (data != -1){
                System.out.println((char) data);
                data = input.read();
            }
            input.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}

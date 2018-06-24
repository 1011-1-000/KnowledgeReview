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
 * @since 2018/6/23 下午11:21
 * @email leo.anonymous@qq.com
 */

// PipeIO is used to the communication between the thread in the system

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipeIO {

    public static void main(String[] args) throws IOException {
        PipedOutputStream output = new PipedOutputStream();
        PipedInputStream input = new PipedInputStream(output);
        Thread outputThread = new Thread(new PipeOutput(output));
        Thread inputThread = new Thread(new PipeInput(input));

        outputThread.start();
        inputThread.start();
    }
}

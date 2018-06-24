package main.com.leo.io;

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
 * @since 2018/6/24 上午9:58
 * @email leo.anonymous@qq.com
 */

import java.io.*;

public class BufferIO {

    public static void bufferWriteToFileByByte(String path) throws IOException {

        String str = "write the string to the file by the byte";
        File file = new File(path + "/bufferWriteByByte.txt");

        OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        os.write(str.getBytes());
//      comment close or not use flush method, the string will not be write to the file.
        os.close();
    }

    public static void bufferReadFromFileByByte(String path) throws IOException {

        File file = new File(path + "/bufferWriteByByte.txt");
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        byte[] byteArray = new byte[(int) file.length()];
        is.read(byteArray);
        is.close();

        System.out.println(new String(byteArray));
    }

    public static void bufferWriteToFileByChar(String path) throws IOException {

        String str = "write the string to the file";
        File file = new File(path + "/bufferWriteToFile.txt");

        Writer writer = new BufferedWriter(new FileWriter(file));

        writer.write(str);
        writer.close();
    }

    public static void bufferReadFromFileByChar(String path) throws IOException {

        File file = new File(path + "/bufferWriteToFile.txt");
        Reader reader = new BufferedReader(new FileReader(file));
        char[] byteArray = new char[(int)(file.length())];
        reader.read(byteArray);
        reader.close();
        System.out.println(byteArray);
    }

    public static void main(String[] args) throws IOException{
        String path = "/Users/leo/Documents/study/KnowledgeReview/Java/resources/io";
        bufferWriteToFileByByte(path);
        bufferReadFromFileByByte(path);
        bufferWriteToFileByChar(path);
        bufferReadFromFileByChar(path);
    }
}

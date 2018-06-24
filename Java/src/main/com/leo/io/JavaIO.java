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

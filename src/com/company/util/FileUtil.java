package com.company.util;

import java.io.*;

/**
 * Created by daisongsong on 2017/5/16.
 */
public class FileUtil {

    public static String readFile(String path) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        File f = new File(path);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            int count = inputStream.read(buffer);
            while (count > 0) {
                byteArrayOutputStream.write(buffer, 0, count);
                count = inputStream.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return byteArrayOutputStream.toString();

    }

    public static void writeFile(String path, String content) {
        File file = new File(path);
        OutputStream outputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

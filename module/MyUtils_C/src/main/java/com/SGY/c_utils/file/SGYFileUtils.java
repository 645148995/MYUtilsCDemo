package com.SGY.c_utils.file;

import android.text.TextUtils;

import com.SGY.c_utils.content.SGYLogUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SGYFileUtils {

    /**
     * 创建文件夹
     */
    public static void createDir(String folder) {
        File dir = new File(folder);
        if (!dir.exists())
            dir.mkdirs();
    }

    /**
     * 创建文件
     *
     * @throws IOException
     */
    public static void createFile(String folder, String fileName)
            throws IOException {
        File file = new File(folder + File.separator + fileName);
        if (!file.exists())
            file.createNewFile();
    }

    /**
     * 创建文件
     *
     * @throws IOException
     */
    public static void createFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists())
            file.createNewFile();
    }

    /**
     * 删除文件
     *
     * @throws IOException
     */
    public static void delFile(String file) {
        try {
            if (TextUtils.isEmpty(file))
                return;
            File f = new File(file);
            if (f.exists()) {
                final File to = new File(f.getAbsolutePath()
                        + System.currentTimeMillis());
                f.renameTo(to);
                to.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹的子目录及文件
     */
    public static void delDir(String folder) {
        File dir = new File(folder);
        if (dir.exists() && dir.isDirectory()) {
            File[] tmp = dir.listFiles();
            for (int i = 0; i < tmp.length; i++) {
                if (tmp[i].isDirectory()) {
                    delDir(folder + File.separator + tmp[i].getName());
                } else {
                    tmp[i].delete();
                }
            }
            dir.delete();
        }
    }

    /**
     * 写入文件
     */
    public static void writerFile(String folder, String fileName,
                                  String content, boolean append) throws IOException {
        createDir(folder);
        createFile(folder, fileName);

        FileWriter writer = new FileWriter(folder + File.separator + fileName,
                append);
        writer.write(content);
        writer.close();
    }

    /**
     * 检测文件是否存在
     */
    public static boolean isFileExists(String file) {
        if (TextUtils.isEmpty(file))
            return false;

        File f = new File(file);
        if (f.exists())
            return true;

        return false;
    }

    /**
     * 以行为单位读取文件
     */
    public static String readFileByLines(String file) {
        File f = new File(file);
        if (!f.exists())
            return null;

        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(f));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            SGYLogUtils.e("以行为单位读取文件读取文件异常：", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return sb.toString();
    }

    /**
     * 将本地文件，转换为字节
     */
    public static byte[] getBytes(String localPath) {
        byte[] buffer = null;
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            File file = new File(localPath);
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            SGYLogUtils.e("将本地文件，转换为字节", e);
        } finally {
            try {
                bos.close();
                fis.close();
            } catch (IOException e) {
            }
        }
        return buffer;
    }

    /**
     * 获得指定目录总大小或单个文件大小
     */
    public static long getDirSize(File file) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getDirSize(child);
        return total;
    }
}

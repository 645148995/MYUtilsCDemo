package com.SGY.c_utils.app;

import com.SGY.c_utils.SGYUtils;
import com.SGY.c_utils.content.SGYLogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * app Assets 目录
 */
public class SGYAssetsUtils {
    /**
     * 读取assets目录中的文件
     *
     * @param fileName
     * @return byte[]
     */
    public static byte[] getAssetsFileToBytes(String fileName) {
        InputStream in = null;
        try {
            in = SGYUtils.getContext().getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int lenght = in.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            return buffer;
        } catch (Exception e) {
            SGYLogUtils.e(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    /**
     * 读取assets目录中的文件
     *
     * @param fileName
     * @return String
     */
    public static String getAssetsFileToString(String fileName) {
        String result = "";
        InputStream in = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            in = SGYUtils.getContext().getResources().getAssets().open(fileName);
            inputReader = new InputStreamReader(in);
            bufReader = new BufferedReader(inputReader);
            String line;
            while ((line = bufReader.readLine()) != null)
                result += line;
        } catch (Exception e) {
            SGYLogUtils.e(e);
        } finally {
            try {
                bufReader.close();
                inputReader.close();
                in.close();
            } catch (IOException e) {
            }
        }
        return result;
    }
}

package com.SGY.c_utils.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自动生成dimens，这个类只供参考，不直接调用。
 * 执行方式：右键文件 -> run...main...
 */
public class DimenGenerateUtils {

    public static void gen() {
        //以此文件夹下的dimens.xml文件内容为初始值参照
        File file = new File("./app/src/main/res/values/dimens.xml");
        BufferedReader reader = null;
        StringBuilder sw240 = new StringBuilder();
        StringBuilder sw320 = new StringBuilder();
        StringBuilder sw360 = new StringBuilder();
        StringBuilder sw420 = new StringBuilder();
        StringBuilder sw440 = new StringBuilder();
        StringBuilder sw480 = new StringBuilder();
        StringBuilder sw560 = new StringBuilder();
        StringBuilder sw600 = new StringBuilder();
        StringBuilder sw640 = new StringBuilder();
        StringBuilder sw720 = new StringBuilder();
        StringBuilder sw800 = new StringBuilder();
        StringBuilder w820 = new StringBuilder();
        try {
//            System.out.println("生成不同分辨率：");
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("</dimen>")) {
                    //tempString = tempString.replaceAll(" ", "");
                    String start = tempString.substring(0, tempString.indexOf(">") + 1);
                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    //截取<dimen></dimen>标签内的内容，从>右括号开始，到左括号减2，取得配置的数字
                    Double num = Double.parseDouble
                            (tempString.substring(tempString.indexOf(">") + 1,
                                    tempString.indexOf("</dimen>") - 2));
                    //根据不同的尺寸，计算新的值，拼接新的字符串，并且结尾处换行。
                    sw240.append(start).append(num * 0.5).append(end).append("\r\n");
                    sw320.append(start).append(num * 0.66).append(end).append("\r\n");
                    sw360.append(start).append(num * 0.75).append(end).append("\r\n");
                    sw420.append(start).append(num * 0.87).append(end).append("\r\n");
                    sw440.append(start).append(num * 0.91).append(end).append("\r\n");
                    //设计图是基于1920*1080 480dpi设计的，所以写1
                    sw480.append(start).append(num * 1).append(end).append("\r\n");
                    sw560.append(start).append(num * 1.16).append(end).append("\r\n");
                    sw600.append(start).append(num * 1.25).append(end).append("\r\n");
                    sw640.append(start).append(num * 1.33).append(end).append("\r\n");
                    sw720.append(start).append(num * 1.5).append(end).append("\r\n");
                    sw800.append(start).append(num * 1.66).append(end).append("\r\n");
                    w820.append(start).append(num * 1.7).append(end).append("\r\n");
                } else {
                    sw240.append(tempString).append("");
                    sw320.append(tempString).append("");
                    sw360.append(tempString).append("");
                    sw420.append(tempString).append("");
                    sw440.append(tempString).append("");
                    sw480.append(tempString).append("");
                    sw560.append(tempString).append("");
                    sw600.append(tempString).append("");
                    sw640.append(tempString).append("");
                    sw720.append(tempString).append("");
                    sw800.append(tempString).append("");
                    w820.append(tempString).append("");
                }
                line++;
            }
            reader.close();

            String sw240file = "./app/src/main/res/values-sw240dp-land/dimens.xml";
            String sw320file = "./app/src/main/res/values-sw320dp-land/dimens.xml";
            String sw360file = "./app/src/main/res/values-sw360dp-land/dimens.xml";
            String sw420file = "./app/src/main/res/values-sw420dp-land/dimens.xml";
            String sw440file = "./app/src/main/res/values-sw440dp-land/dimens.xml";
            String sw480file = "./app/src/main/res/values-sw480dp-land/dimens.xml";
            String sw560file = "./app/src/main/res/values-sw560dp-land/dimens.xml";
            String sw600file = "./app/src/main/res/values-sw600dp-land/dimens.xml";
            String sw640file = "./app/src/main/res/values-sw640dp-land/dimens.xml";
            String sw720file = "./app/src/main/res/values-sw720dp-land/dimens.xml";
            String sw800file = "./app/src/main/res/values-sw800dp-land/dimens.xml";
            String w820file = "./app/src/main/res/values-w820dp/dimens.xml";

            //将新的内容，写入到指定的文件中去
            writeFile(sw240file, sw240.toString());
            writeFile(sw320file, sw320.toString());
            writeFile(sw360file, sw360.toString());
            writeFile(sw420file, sw420.toString());
            writeFile(sw440file, sw440.toString());
            writeFile(sw480file, sw480.toString());
            writeFile(sw560file, sw560.toString());
            writeFile(sw600file, sw600.toString());
            writeFile(sw640file, sw640.toString());
            writeFile(sw720file, sw720.toString());
            writeFile(sw800file, sw800.toString());
            writeFile(w820file, w820.toString());
//            System.out.println("完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void writeFile(String file, String text) {
        PrintWriter out = null;
        try {
            File folder = new File(file.substring(0, file.lastIndexOf("/")));
            if (!folder.exists())
                folder.mkdirs();
            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }

    public static void main(String[] args) {
        gen();
    }
}

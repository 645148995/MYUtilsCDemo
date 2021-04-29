package com.SGY.c_utils.file;

import com.SGY.c_utils.content.SGYLogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 对象的存取
 */
public class SGYFileObjectUtils {

    /**
     * 文件转化为Object
     *
     * @param fileName
     * @return byte[]
     */
    public static Object file2Object(String fileName) {
        return SGYFileObjectUtils.file2Object(fileName, null);
    }

    /**
     * 文件转化为Object
     *
     * @param fis
     * @return byte[]
     */
    public static Object file2Object(FileInputStream fis) {
        return SGYFileObjectUtils.file2Object(null, fis);
    }

    private static Object file2Object(String fileName, FileInputStream fis) {
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            return object;
        } catch (Exception e) {
            SGYLogUtils.e(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    SGYLogUtils.e(e1);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e2) {
                    SGYLogUtils.e(e2);
                }
            }
        }
        return null;
    }

    /**
     * object转化为文件
     *
     * @param obj
     * @param outputFile
     */
    public static void object2File(Object obj, String outputFile) {
        SGYFileObjectUtils.object2File(obj, outputFile, null);
    }

    /**
     * object转化为文件
     *
     * @param obj
     * @param fos
     */
    public static void object2File(Object obj, FileOutputStream fos) {
        SGYFileObjectUtils.object2File(obj, null, fos);
    }

    /**
     * object转化为文件
     *
     * @param obj
     * @param outputFile
     */
    private static void object2File(Object obj, String outputFile, FileOutputStream fos) {
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(new File(outputFile));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (Exception e) {
            SGYLogUtils.e(e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e1) {
                    SGYLogUtils.e(e1);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e2) {
                    SGYLogUtils.e(e2);
                }
            }
        }
    }
}

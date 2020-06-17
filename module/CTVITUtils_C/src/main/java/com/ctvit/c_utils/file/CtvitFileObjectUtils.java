package com.ctvit.c_utils.file;

import com.ctvit.c_utils.content.CtvitLogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 对象的存取
 */
public class CtvitFileObjectUtils {

    /**
     * 文件转化为Object
     *
     * @param fileName
     * @return byte[]
     */
    public static Object file2Object(String fileName) {
        return CtvitFileObjectUtils.file2Object(fileName, null);
    }

    /**
     * 文件转化为Object
     *
     * @param fis
     * @return byte[]
     */
    public static Object file2Object(FileInputStream fis) {
        return CtvitFileObjectUtils.file2Object(null, fis);
    }

    private static Object file2Object(String fileName, FileInputStream fis) {
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            return object;
        } catch (Exception e) {
            CtvitLogUtils.e(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    CtvitLogUtils.e(e1);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e2) {
                    CtvitLogUtils.e(e2);
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
        CtvitFileObjectUtils.object2File(obj, outputFile, null);
    }

    /**
     * object转化为文件
     *
     * @param obj
     * @param fos
     */
    public static void object2File(Object obj, FileOutputStream fos) {
        CtvitFileObjectUtils.object2File(obj, null, fos);
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
            CtvitLogUtils.e(e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e1) {
                    CtvitLogUtils.e(e1);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e2) {
                    CtvitLogUtils.e(e2);
                }
            }
        }
    }
}

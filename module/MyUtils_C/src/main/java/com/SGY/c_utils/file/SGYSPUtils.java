package com.SGY.c_utils.file;

import android.content.Context;
import android.content.SharedPreferences;

import com.SGY.c_utils.SGYUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

/**
 * Created by lenovo on 2016/12/22.
 * SharedPreferences 存储工具类
 * 对SharedPreference的使用做了建议的封装，对外公布出put，get，remove，clear等等方法；
 * <p>
 * 注意一点，里面所有的commit操作使用了SharedPreferencesCompat.apply进行了替代，目的是尽可能的使用apply代替commit
 * <p>
 * 首先说下为什么，因为commit方法是同步的，并且我们很多时候的commit操作都是UI线程中，毕竟是IO操作，尽可能异步；
 * <p>
 * 所以我们使用apply进行替代，apply异步的进行写入；
 * <p>
 * 但是apply相当于commit来说是new API呢，为了更好的兼容，我们做了适配；
 */

public final class SGYSPUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = SGYSPUtils.class.getName().replace(".", "_").toUpperCase(Locale.CHINA);

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {
        put(key, object, false);
    }

    public static void put(String fileName, String key, Object object) {
        put(fileName, key, object, false);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     * @param apply  如果希望立刻获取存储操作的结果，并据此做相应的其他操作，请传false，反之传true
     */
    public static void put(String key, Object object, boolean apply) {
        put(FILE_NAME, key, object, apply);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param fileName 自定义存储的文件名称
     * @param key
     * @param object
     * @param apply    如果希望立刻获取存储操作的结果，并据此做相应的其他操作，请传false，反之传true
     */
    public static void put(String fileName, String key, Object object, boolean apply) {
        SharedPreferences sp = SGYUtils.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor, apply);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        return get(FILE_NAME, key, defaultObject);
    }

    public static Object get(String fileName, String key, Object defaultObject) {
        SharedPreferences sp = SGYUtils.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        remove(key, false);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     * @param apply 如果希望立刻获取存储操作的结果，并据此做相应的其他操作，请传false，反之传true
     */
    public static void remove(String key, boolean apply) {
        remove(FILE_NAME, key, apply);
    }

    public static void remove(String fileName, String key, boolean apply) {
        SharedPreferences sp = SGYUtils.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor, apply);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        clear(false);
    }

    /**
     * 清除所有数据
     *
     * @param apply 如果希望立刻获取存储操作的结果，并据此做相应的其他操作，请传false，反之传true
     */
    public static void clear(boolean apply) {
        clear(FILE_NAME, apply);
    }

    public static void clear(String fileName, boolean apply) {
        SharedPreferences sp = SGYUtils.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor, apply);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        return contains(FILE_NAME, key);
    }

    public static boolean contains(String fileName, String key) {
        SharedPreferences sp = SGYUtils.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        return getAll(FILE_NAME);
    }

    public static Map<String, ?> getAll(String fileName) {
        SharedPreferences sp = SGYUtils.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         * @param apply  如果希望立刻获取存储操作的结果，并据此做相应的其他操作，请传false
         */
        public static void apply(SharedPreferences.Editor editor, boolean apply) {
            try {
                if (apply && sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}

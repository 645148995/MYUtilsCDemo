package com.SGY.c_utils.device;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.SGY.c_utils.SGYUtils;


/**
 * 软键盘控制
 */
public class SGYSoftKeyBoardUtils {

    /**
     * 弹出软键盘
     */
    public static void showSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) SGYUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && imm != null) {
            imm.showSoftInput(view, 0);
            // imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT); // 或者第二个参数传InputMethodManager.SHOW_IMPLICIT
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) SGYUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            // imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY); // 或者第二个参数传InputMethodManager.HIDE_IMPLICIT_ONLY
        }
    }
}

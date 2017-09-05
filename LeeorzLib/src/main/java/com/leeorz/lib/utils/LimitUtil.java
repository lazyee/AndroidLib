package com.leeorz.lib.utils;

import android.widget.EditText;

/**
 * Desc: 限制输入
 * Created by 庞承晖
 * Date: 2015/9/16.
 * Time: 14:16
 */
public class LimitUtil {

    /**
     * 限制小数
     *
     * @param editText
     * @param s
     */
    public static void limitDecimal(EditText editText, CharSequence s) {
        // 只允许输入小数点后两位
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                editText.setText(s);
                editText.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }

        if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
                return;
            }
        }
    }


}

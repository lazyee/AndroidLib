package com.leeorz.lib.utils;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * Desc: 复制黏贴
 * Created by 庞承晖
 * Date: 2015/10/21.
 * Time: 18:22
 */
public class ClipUtil {

    /**
     * 复制
     *
     * @param context
     * @param str
     */
    public static void copy(Context context, String str) {
        copy(context, str, false);
    }

    /**
     * 复制
     *
     * @param context
     * @param str
     */
    public static void copy(Context context, String str, boolean showToast) {
        ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(str); // 复制
        if (showToast)
            ToastUtil.showShort(context, "文本已复制");
    }

    /**
     * 复制
     *
     * @param context
     * @param str
     */
    public static void copy(Context context, String str, String text) {
        ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(str); // 复制
        if (text != null)
            ToastUtil.showShort(context, text);
    }

    /**
     * 黏贴
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return clip.getText().toString(); //黏贴
    }
}

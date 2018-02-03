package com.fire.baselibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Toast;

/**
 *
 * @author fire
 * @date 2017/12/28
 * Description:
 */

public class ToastUtils {

    private static Context mContext;
    private static Toast toast;

    public static void init(Context context) {
        mContext = context;
    }

    public static void showToast(String text) {
        showToast(mContext, text);
    }

    public static void showToast(Context context, String text) {
        showToast(context, text,Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String text,int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, text, duration);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void showToast(int resId) throws Resources.NotFoundException {
        showToast(mContext, mContext.getResources().getText(resId));
    }

    public static void showToast(Context context, int resId) throws Resources.NotFoundException {
        showToast(context, context.getResources().getText(resId));
    }

    public static void showToast(Context context, int resId, int duration) throws Resources.NotFoundException {
        showToast(context, context.getResources().getText(resId),duration);
    }

    public static void showToast( CharSequence text) {
        showToast(mContext, text);
    }

    public static void showToast(Context context, CharSequence text) {
        showToast(context, text.toString());
    }

    public static void showToast(Context context, CharSequence text,int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, text, duration);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void showToast(Context context, View view){
        if (toast == null) {
            toast=new Toast(context);
        }
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showToast(View view){
        showToast(mContext,view);
    }

}

package com.fire.baselibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fire.baselibrary.R;

/**
 * Created by fire on 2018/1/16.
 * Date：2018/1/16
 * Author: fire
 * Description:
 */

public class CustomerDialog {

    private static Dialog mCustomDialog = null;

    /**
     * 提示信息为 正在加载数据...
     *
     * @param context
     * @return
     */
    public static Dialog showProgress(Activity context) {
        return showProgress(context, context.getResources().getString(R.string.loading_data_str), false);
    }

    /**
     * 显示加载dialog
     *
     * @param context
     * @param tips
     * @return
     */
    public static Dialog showProgress(Activity context, String tips, boolean isOutCancelable) {
        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            return mCustomDialog;
        } else {
            mCustomDialog = new Dialog(context, R.style.ImageloadingDialogStyle);
            mCustomDialog.setCancelable(true);
            mCustomDialog.setCanceledOnTouchOutside(isOutCancelable);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, new ViewGroup(context) {
                @Override
                protected void onLayout(boolean changed, int l, int t, int r, int b) {
                }
            }, false);
            mCustomDialog.setContentView(view);
            ((TextView) view.findViewById(R.id.tv_tips)).setText(tips);
            mCustomDialog.show();
            return mCustomDialog;
        }
    }

    /**
     * 隐藏Dialog
     */
    public static void dismissProgress() {
        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            mCustomDialog.dismiss();
            mCustomDialog = null;
        }
    }

    /**
     * 更新Dialog信息
     */

    public static void updateProgressMessage(String msg) {
        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            View view = mCustomDialog.findViewById(R.id.tv_tips);
            if(view != null) ((TextView)view).setText(msg);
        }
    }

    public static abstract class DialogClickListener {
        public abstract void confirm();

        public void cancel() {}
    }

}

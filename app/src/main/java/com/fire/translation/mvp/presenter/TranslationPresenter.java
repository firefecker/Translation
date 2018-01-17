package com.fire.translation.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.translation.R;
import com.fire.translation.mvp.model.TranslationModel;
import com.fire.translation.mvp.view.TranslationView;
import com.fire.translation.widget.EventBase;
import com.fire.translation.widget.ListPopupWindow;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */

public class TranslationPresenter implements IBasePresenter, ListPopupWindow.DataBack {

    private TranslationModel mTranslationModel;
    private TranslationView mTranslationView;
    private ListPopupWindow mListPopupWindow;

    public TranslationPresenter(TranslationView mTranslationView) {
        this.mTranslationView = mTranslationView;
        this.mTranslationModel = new TranslationModel();
    }

    public void init(Activity activity) {
        LinearInterpolator lin1 = new LinearInterpolator();
        LinearInterpolator lin2 = new LinearInterpolator();
        LinearInterpolator lin3 = new LinearInterpolator();
        Animation mOperatingAnim1 = AnimationUtils.loadAnimation(activity, R.anim.roate_0_180);
        Animation mOperatingAnim2 = AnimationUtils.loadAnimation(activity, R.anim.roate_180_360);
        Animation mOperatingAnim3 = AnimationUtils.loadAnimation(activity, R.anim.roate_0_180);
        mOperatingAnim1.setInterpolator(lin1);
        mOperatingAnim1.setFillAfter(true);
        mOperatingAnim2.setInterpolator(lin2);
        mOperatingAnim2.setFillAfter(true);
        mOperatingAnim3.setInterpolator(lin3);
        mOperatingAnim3.setFillAfter(true);
        List<String> mStrings = Arrays.asList(
                activity.getResources().getStringArray(R.array.languages));
        mTranslationView.initData(mOperatingAnim1, mOperatingAnim2, mOperatingAnim3, mStrings);
    }

    public void translate(String from, String to, String string) {
        mTranslationModel.translate(mTranslationView, from, to, string);
    }


    public void showPopup(Activity activity, LinearLayout mLayoutLanguage, LinearLayout layoutFrom,
            List<String> strings, ImageView transView, Animation mOperatingAnim1) {
        initPopupWindow(activity, mLayoutLanguage);
        mListPopupWindow.setData(strings);
        transView.startAnimation(mOperatingAnim1);
        if (mListPopupWindow != null && !mListPopupWindow.isShowing()) {
            mListPopupWindow.showAsDropDown(layoutFrom, 10, 10);
        }
    }

    public void initPopupWindow(Activity activity, LinearLayout mLayoutLanguage) {
        if (mListPopupWindow == null) {
            mListPopupWindow = new ListPopupWindow(activity, mLayoutLanguage.getWidth());
            mListPopupWindow.setBackgroundDrawable(
                    new BitmapDrawable(activity.getResources(), (Bitmap) null));
            mListPopupWindow.setOutsideTouchable(true); //点击外部关闭。
            mListPopupWindow.setFocusable(true);
            mListPopupWindow.setOutsideTouchable(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mListPopupWindow.setElevation(6f);
            }
            mListPopupWindow.setOnDismissListener(() -> {
                mListPopupWindow.dismiss();
            });
            mListPopupWindow.setDataBack(this);
        }
    }

    @Override
    public void stringBack(View view, String data) {
        mTranslationView.stringBack(view, data);
    }

    @Override
    public void dismissBack(View view) {
        mTranslationView.dismissBack(view);
    }

    public void zipFile(EventBase eventBase,Context context) {
        mTranslationModel.zipFile(mTranslationView,eventBase,context);
    }

    public void rxBus(Class mClass) {
        mTranslationView.rxBus(mTranslationModel.rxBus(mClass));
    }
}

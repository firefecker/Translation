package com.fire.translation.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.baselibrary.utils.ListUtils;
import com.fire.baselibrary.utils.ToastUtils;
import com.fire.translation.R;
import com.fire.translation.db.entities.Tanslaterecord;
import com.fire.translation.mvp.model.TranslationModel;
import com.fire.translation.mvp.view.TranslationView;
import com.fire.baselibrary.rx.EventBase;
import com.fire.translation.widget.ListPopupWindow;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.sunflower.FlowerCollector;
import com.youdao.ocr.online.OCRResult;
import com.youdao.sdk.ydtranslate.Translate;
import io.reactivex.functions.Function;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */

public class TranslationPresenter implements IBasePresenter<Tanslaterecord>, ListPopupWindow.DataBack {

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
        Set<String> strings = new HashSet<>();
        strings.add("1");
        strings.add("3");
        Set<String> language_select = PreferenceManager.getDefaultSharedPreferences(activity)
                .getStringSet("language_select", strings);
        mTranslationView.initData(mOperatingAnim1, mOperatingAnim2, mOperatingAnim3,
                ListUtils.setToList(activity, R.array.plan, R.array.languages, language_select));
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

    public void zipFile(EventBase eventBase, Context context) {
        mTranslationModel.zipFile(mTranslationView, eventBase, context);
    }

    public SpeechSynthesizer setParam(Context context) {
        // 清空参数
        //mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(context,
                TranslationView.mTtsInitListener);
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,
                Environment.getExternalStorageDirectory() + "/translation/tts.wav");
        return mTts;
    }

    public void speak(String content, SpeechSynthesizer mTts, Context context) {
        // 移动数据分析，收集开始合成事件
        FlowerCollector.onEvent(context, "tts_play");
        // 设置参数
        int code = mTts.startSpeaking(content, TranslationView.mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            ToastUtils.showToast("语音合成失败,错误码: " + code);
        }
    }

    @Override
    public void rxBus(Class mClass, Class aClass) {
        Class aClazz = aClass;
        mTranslationView.rxBus(mTranslationModel.rxBus(mClass).map(eventBase -> {
            if (eventBase.getReceiver() != aClazz) {
                return null;
            } else {
                return eventBase;
            }
        }));
    }

    public Function<OCRResult, String> setOcrResult() {
        return mTranslationModel.setOcrResult();
    }

    public Function<Translate, Tanslaterecord> settranslateResult() {
        return mTranslationModel.settranslateResult();
    }

    @Override
    public void updateStar(Tanslaterecord listTranslate) {
        mTranslationModel.updateStar(mTranslationView, listTranslate);
    }

    public void getAllTranslateRecord() {
        mTranslationView.getAllTranslateRecord(mTranslationModel.getAllTranslateRecord());
    }

    public void loadTranslateRecord() {
        mTranslationView.loadTranslateRecord(mTranslationModel.loadTranslateRecord());
    }
}

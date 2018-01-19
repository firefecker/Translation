package com.fire.translation.mvp.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.baselibrary.utils.ToastUtils;
import com.fire.translation.db.entities.Tanslaterecord;
import com.fire.baselibrary.rx.EventBase;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.orhanobut.logger.Logger;
import com.pushtorefresh.storio3.sqlite.Changes;
import com.youdao.ocr.online.OCRResult;
import com.youdao.ocr.online.OcrErrorCode;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */

public interface TranslationView extends IBaseView{
    void initData(Animation mOperatingAnim1, Animation mOperatingAnim2, Animation mOperatingAnim3,
            List<String> mStrings);

    void translateOnError(Observable<TranslateErrorCode> translateErrorCode);

    void translateOnResult(Observable<Translate> translateObservable);

    void stringBack(View view, String data);

    void dismissBack(View view);

    void ocrOnResult(Observable<OCRResult> ocrResultObservable);

    void ocrOnError(Observable<OcrErrorCode> ocrResultObservable);


    void starFailure(Throwable throwable);

    void starSuccess(EventBase putResult);

    void loadTranslateRecord(Flowable<Changes> changesFlowable);

    void getAllTranslateRecord(Flowable<List<Tanslaterecord>> allTranslateRecord);

    /**
     * 初始化监听。
     */
    InitListener mTtsInitListener = code -> {
        Logger.d("InitListener init() code = " + code);
        if (code != ErrorCode.SUCCESS) {
            ToastUtils.showToast("初始化失败,错误码：" + code);
        } else {
            // 初始化成功，之后可以调用startSpeaking方法
            // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
            // 正确的做法是将onCreate中的startSpeaking调用移至这里
        }
    };

    /**
     * 合成回调监听。
     */
    SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            //ToastUtils.showToast("开始播放");
        }

        @Override
        public void onSpeakPaused() {
            //ToastUtils.showToast("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            //ToastUtils.showToast("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                String info) {
            // 合成进度
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                //ToastUtils.showToast("播放完成");
            } else if (error != null) {
                ToastUtils.showToast(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };
}

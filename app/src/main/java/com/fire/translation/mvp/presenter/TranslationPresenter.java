package com.fire.translation.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
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
import com.fire.translation.utils.JsonParser;
import com.fire.translation.widget.ListPopupWindow;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;
import com.youdao.ocr.online.OCRResult;
import com.youdao.sdk.ydtranslate.Translate;
import io.reactivex.functions.Function;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

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

    public SpeechSynthesizer setTtsParam(Context context) {
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

    /**
     * 初始化监听器。
     */
    public InitListener mInitListener = code -> {
        if (code != ErrorCode.SUCCESS) {
            ToastUtils.showToast("初始化失败，错误码：" + code);
        }
    };

    /**
     * 听写UI监听器
     */
    public RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            if( false ){
                mTranslationView.printTransResult(results);
            }else{
                mTranslationView.printResult(results);
            }
        }
        /**
         * 识别回调错误.
         */
        @Override
        public void onError(SpeechError error) {
            if(false && error.getErrorCode() == 14002) {
                ToastUtils.showToast( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
            } else {
                ToastUtils.showToast(error.getPlainDescription(true));
            }
        }
    };

    /**
     * 听写监听器。
     */
    public RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            ToastUtils.showToast("开始说话");
            mTranslationView.onBeginOfSpeech();
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            if(false && error.getErrorCode() == 14002) {
                ToastUtils.showToast( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
            } else {
                ToastUtils.showToast(error.getPlainDescription(true));
            }
            mTranslationView.onSpeakError(error);
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            ToastUtils.showToast("说话结束");
            mTranslationView.onEndOfSpeech();
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            if( false ){
                mTranslationView.printTransResult( results );
            }else{
                mTranslationView.printResult(results);
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            //ToastUtils.showToast("当前正在说话，音量大小：" + volume);
            mTranslationView.onVolumeChanged(volume);
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

    public SpeechRecognizer setIatParam(Context context) {
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(context, mInitListener);
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        boolean mTranslateEnable = false;
        if( mTranslateEnable ){
            mIat.setParameter( SpeechConstant.ASR_SCH, "1" );
            mIat.setParameter( SpeechConstant.ADD_CAP, "translate" );
            mIat.setParameter( SpeechConstant.TRS_SRC, "its" );
        }
        //普通话
        String lag =  "mandarin";
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);

            if( mTranslateEnable ){
                mIat.setParameter( SpeechConstant.ORI_LANG, "en" );
                mIat.setParameter( SpeechConstant.TRANS_LANG, "cn" );
            }
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);

            if( mTranslateEnable ){
                mIat.setParameter( SpeechConstant.ORI_LANG, "cn" );
                mIat.setParameter( SpeechConstant.TRANS_LANG, "en" );
            }
        }
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "5000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS,"2000");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");

        //// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        //// 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
        return mIat;

    }

    public void startAudio(Context context,HashMap<String, String> iatResults, SpeechRecognizer iat) {
        // 移动数据分析，收集开始听写事件
        FlowerCollector.onEvent(context, "iat_recognize");
        iatResults.clear();
        int ret = iat.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            ToastUtils.showToast("听写失败,错误码：" + ret);
        } else {
            ToastUtils.showToast(context.getString(R.string.text_begin));
        }
    }

    public StringBuffer parseResut(RecognizerResult recognizerResult, HashMap<String, String> iatResults) {
        String text = JsonParser.parseIatResult(recognizerResult.getResultString());
        String sn = null;
        try {
            JSONObject resultJson = new JSONObject(recognizerResult.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iatResults.put(sn, text);
        StringBuffer resultBuffer = new StringBuffer();
        for (String key : iatResults.keySet()) {
            resultBuffer.append(iatResults.get(key));
        }
        return resultBuffer;
    }

    public void sleepAfterUpdate() {
        mTranslationView.sleepAfterUpdate(mTranslationModel.sleepAfterUpdate());
    }
}

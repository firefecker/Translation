package com.fire.translation.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.AnimRes;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.baselibrary.utils.ListUtils;
import com.fire.baselibrary.utils.ToastUtils;
import com.fire.translation.R;
import com.fire.translation.mvp.model.WordbookModel;
import com.fire.translation.mvp.view.WordbookView;
import com.fire.translation.ui.fragment.WordbookFragment;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.sunflower.FlowerCollector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by fire on 2018/1/22.
 * Date：2018/1/22
 * Author: fire
 * Description:
 */

public class WordbookPresenter implements IBasePresenter {

    private WordbookModel mWordbookModel;
    private WordbookView mWordbookView;

    public WordbookPresenter(WordbookView wordbookView) {
        mWordbookView = wordbookView;
        mWordbookModel = new WordbookModel();
    }

    @Override
    public void rxBus(Class mClass, Class aClass) {
        mWordbookView.rxBus(mWordbookModel.rxBus(mClass).map(eventBase -> eventBase));
    }

    public void loadData(int newWord,int remomber) {
        mWordbookView.loadData(mWordbookModel.loadData(newWord, remomber));
    }

    public void startSpeak(String content) {
        mWordbookView.startSpeak(content);
    }

    public SpeechSynthesizer setParam(Context context) {
        // 清空参数
        //mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(context, mWordbookView.mTtsInitListener);
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
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/translation/tts.wav");
        return mTts;
    }

    public void speak(String content, SpeechSynthesizer tts, Context context) {
        // 移动数据分析，收集开始合成事件
        FlowerCollector.onEvent(context, "tts_play");
        // 设置参数
        int code = tts.startSpeaking(content, WordbookView.mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            ToastUtils.showToast("语音合成失败,错误码: " + code);
        }
    }

    public Animation getAnimation(Context context,@AnimRes int anim) {
        LinearInterpolator lin1 = new LinearInterpolator();
        Animation mOperatingAnim1 = AnimationUtils.loadAnimation(context, anim);
        mOperatingAnim1.setInterpolator(lin1);
        mOperatingAnim1.setFillAfter(true);
        return mOperatingAnim1;
    }

    public void initRememberAndWord(Activity activity) {
        int remomber,newWord;
        HashSet<String> strings = new HashSet<>();
        strings.add("1");
        Set<String> wordSelect = PreferenceManager.getDefaultSharedPreferences(activity)
                .getStringSet("word_select", strings);
        if (wordSelect.size() == 0) {
            remomber = 0;
            newWord = 0;
        } else if (wordSelect.size() == 1) {
            if ("1".equals(wordSelect.iterator().next())) {
                remomber = 1;
                newWord = 0;
            } else {
                remomber = 0;
                newWord = 1;
            }
        } else {
            remomber = 1;
            newWord = 1;
        }
        mWordbookView.initRememberAndWord(remomber,newWord);
    }
}

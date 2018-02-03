package com.fire.translation.mvp.presenter;

import android.content.Context;
import android.os.Environment;
import android.widget.CheckBox;
import android.widget.Switch;
import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.baselibrary.utils.ToastUtils;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.Word;
import com.fire.translation.mvp.model.ReviewModel;
import com.fire.translation.mvp.view.ReviewView;
import com.fire.translation.utils.DateUtils;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.sunflower.FlowerCollector;
import java.util.Date;

/**
 *
 * @author fire
 * @date 2018/1/18
 * Description:
 */

public class ReviewPresenter implements IBasePresenter {

    private ReviewModel mReviewModel;
    private ReviewView mReviewView;

    public ReviewPresenter(ReviewView reviewView) {
        mReviewView = reviewView;
        mReviewModel = new ReviewModel();
    }

    public void loadData(int reviews) {
        mReviewView.loadData(mReviewModel.loadData(reviews));
    }

    public void setUpDateRememberStatus(Switch mSwRemember, Word word) {
        boolean status = false;
        if (word.getRemember() == 0) {
            status = true;
            word.setRemember(1);
            word.setTime(DateUtils.formatDateToString(new Date(),DateUtils.dateFormat1));
        } else {
            status = false;
            word.setRemember(0);
            if (word.getNewWord() == 0) {
                word.setTime(null);
            } else {
                word.setTime(DateUtils.formatDateToString(new Date(),DateUtils.dateFormat1));
            }
        }
        mReviewView.setUpDateRememberStatus(mSwRemember,mReviewModel.setUpDateRememberStatus(word),status);
    }

    public void updateRecordWords(Record record) {
        mReviewView.updateRecordWords(mReviewModel.updateRecordWords(record));
    }

    public void setUpDateNewwordStatus(CheckBox mCbAdd, Word word) {
        boolean status = false;
        if (word.getNewWord() == 0) {
            status = true;
            word.setNewWord(1);
            word.setTime(DateUtils.formatDateToString(new Date(),DateUtils.dateFormat1));
        } else {
            status = false;
            word.setNewWord(0);
            if (word.getRemember() == 0) {
                word.setTime(null);
            } else {
                word.setTime(DateUtils.formatDateToString(new Date(),DateUtils.dateFormat1));
            }
        }
        mReviewView.setUpDateNewwordStatus(mCbAdd,mReviewModel.setUpDateRememberStatus(word),status);
    }

    public void startSpeak(String content) {
        mReviewView.startSpeak(content);
    }

    public SpeechSynthesizer setParam(Context context) {
        // 清空参数
        //mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(context, mReviewView.mTtsInitListener);
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

    public void speak(String content, SpeechSynthesizer mTts, Context context) {
        // 移动数据分析，收集开始合成事件
        FlowerCollector.onEvent(context, "tts_play");
        // 设置参数
        int code = mTts.startSpeaking(content, mReviewView.mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            ToastUtils.showToast("语音合成失败,错误码: " + code);
        }
    }
}

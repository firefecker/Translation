package com.fire.translation.mvp.model;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import com.fire.baselibrary.base.inter.IBaseModel;
import com.fire.baselibrary.utils.ToastUtils;
import com.fire.translation.TransApplication;
import com.fire.translation.mvp.view.TranslationView;
import com.fire.translation.utils.FileUtils;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;
import java.io.File;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */

public class TranslationModel implements IBaseModel{


    public void translate(TranslationView translationView,String from, String to, String string) {
        //查词对象初始化，请设置source参数为app对应的名称（英文字符串）
        Language langFrom = LanguageUtils.getLangByName(from);
        //若设置为自动，则查询自动识别源语言，自动识别不能保证完全正确，最好传源语言类型
        //Language langFrom = LanguageUtils.getLangByName("自动");
        Language langTo = LanguageUtils.getLangByName(to);
        TranslateParameters tps = new TranslateParameters.Builder().source("Transaction").from(langFrom).to(langTo).timeout(3000).build();
        Translator translator = Translator.getInstance(tps);
        //查询，返回两种情况，一种是成功，相关结果存储在result参数中，另外一种是失败，失败信息放在TranslateErrorCode 是一个枚举类，整个查询是异步的，为了简化操作，回调都是在主线程发生。
        translator.lookup(string, "requestId", new TranslateListener() {
            @Override
            public void onError(TranslateErrorCode translateErrorCode, String s) {
                translationView.translateOnError(translateErrorCode,s);
            }

            @Override
            public void onResult(Translate translate, String s, String s1) {
                translationView.translateOnResult(translate,s,s1);
            }
        });
    }

    public String takePhoto(Fragment fragment) {
        String state = Environment.getExternalStorageState(); // 判断是否存在sd卡
        String filePath = null;
        if (state.equals(Environment.MEDIA_MOUNTED)) { // 直接调用系统的照相机
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            filePath = FileUtils.getFileName();
            Uri uri = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(TransApplication.mTransApp, "com.fire.translation.ui.fileprovider", new File(filePath));
            } else {
                uri = Uri.fromFile(new File(filePath));
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            fragment.startActivityForResult(intent, 1);
        } else {
            ToastUtils.showToast("请检查手机是否有SD卡");
        }
        return filePath;
    }
}

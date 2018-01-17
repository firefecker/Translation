package com.fire.translation.mvp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.fire.baselibrary.base.inter.IBaseModel;
import com.fire.translation.mvp.view.TranslationView;
import com.fire.translation.rx.BaseOnCompressListener;
import com.fire.translation.rx.DefaultButtonTransformer;
import com.fire.translation.rx.DefaultObservable;
import com.fire.translation.rx.RxBus;
import com.fire.translation.utils.FunctionUtils;
import com.fire.translation.widget.EventBase;
import com.youdao.ocr.online.ImageOCRecognizer;
import com.youdao.ocr.online.OCRListener;
import com.youdao.ocr.online.OCRParameters;
import com.youdao.ocr.online.OCRResult;
import com.youdao.ocr.online.OcrErrorCode;
import com.youdao.sdk.app.EncryptHelper;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;
import io.reactivex.Observable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import top.zibin.luban.Luban;

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
                translationView.translateOnError(DefaultObservable.create(translateErrorCode));
            }

            @Override
            public void onResult(Translate translate, String s, String s1) {
                translationView.translateOnResult(DefaultObservable.create(translate));
            }
        });
    }


    public void ocrBitmap(TranslationView translationView, Bitmap bitmap) {
        OCRParameters tps = new OCRParameters.Builder()
                .source("TransactionORC")
                .timeout(100000)
                .type("10011")
                .lanType("zh-en")
                .build();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] datas = baos.toByteArray();
        String bases64 = EncryptHelper.getBase64(datas);
        int count = bases64.length();
        while (count > 1.4 * 1024 * 1024) {
            quality = quality - 10;
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            datas = baos.toByteArray();
            bases64 = EncryptHelper.getBase64(datas);
        }
        final String base64 = bases64;
        ImageOCRecognizer.getInstance(tps).recognize(base64,
                new OCRListener() {
                    @Override
                    public void onResult(final OCRResult result, String input) {
                        translationView.ocrOnResult(DefaultObservable.create(result));
                    }

                    @Override
                    public void onError(final OcrErrorCode error) {
                        translationView.ocrOnError(DefaultObservable.create(error));
                    }
                });
    }

    public void zipFile(TranslationView mTranslationView,EventBase eventBase, Context context) {
        File imageFile = null;
        if (TextUtils.isEmpty(eventBase.getArg2())) {
            imageFile = FunctionUtils.getImageFile();
        } else {
            imageFile = new File(eventBase.getArg2());
        }
        Luban.with(context)
                // 传人要压缩的图片列表
                .load(imageFile)
                // 忽略不压缩图片的大小
                .ignoreBy(100)
                //设置回调
                .setCompressListener(new BaseOnCompressListener() {
                    @Override
                    public void onSuccess(File file) {
                        if (TextUtils.isEmpty(eventBase.getArg2())) {
                            FunctionUtils.getImageFile().delete();
                        }
                        ocrBitmap(mTranslationView,FunctionUtils.readBitmapFromFile(file.getAbsolutePath(),768));
                    }
                })
                //启动压缩
                .launch();
    }

    public Observable<EventBase> rxBus(Class mClass) {
        return RxBus.getDefault()
                .toObservable(mClass)
                .compose(DefaultButtonTransformer.create());
    }
}

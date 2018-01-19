package com.fire.translation.mvp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.fire.baselibrary.base.inter.IBaseModel;
import com.fire.translation.db.Dbservice;
import com.fire.translation.db.entities.Tanslaterecord;
import com.fire.translation.mvp.view.TranslationView;
import com.fire.translation.rx.BaseOnCompressListener;
import com.fire.translation.rx.DefaultObservable;
import com.fire.translation.utils.FunctionUtils;
import com.fire.translation.utils.ListUtils;
import com.fire.baselibrary.rx.EventBase;
import com.orhanobut.logger.Logger;
import com.pushtorefresh.storio3.sqlite.Changes;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import com.youdao.ocr.online.ImageOCRecognizer;
import com.youdao.ocr.online.Line;
import com.youdao.ocr.online.OCRListener;
import com.youdao.ocr.online.OCRParameters;
import com.youdao.ocr.online.OCRResult;
import com.youdao.ocr.online.OcrErrorCode;
import com.youdao.ocr.online.Region;
import com.youdao.ocr.online.Word;
import com.youdao.sdk.app.EncryptHelper;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import top.zibin.luban.Luban;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */

public class TranslationModel implements IBaseModel {

    public void translate(TranslationView translationView, String from, String to, String string) {
        //查词对象初始化，请设置source参数为app对应的名称（英文字符串）
        Language langFrom = LanguageUtils.getLangByName(from);
        //若设置为自动，则查询自动识别源语言，自动识别不能保证完全正确，最好传源语言类型
        //Language langFrom = LanguageUtils.getLangByName("自动");
        Language langTo = LanguageUtils.getLangByName(to);
        TranslateParameters tps = new TranslateParameters.Builder().source("Transaction")
                .from(langFrom)
                .to(langTo)
                .timeout(3000)
                .build();
        Translator translator = Translator.getInstance(tps);
        //查询，返回两种情况，一种是成功，相关结果存储在result参数中，另外一种是失败，失败信息放在TranslateErrorCode
        // 是一个枚举类，整个查询是异步的，为了简化操作，回调都是在主线程发生。
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
        if (bitmap == null) {
            OcrErrorCode error = OcrErrorCode.INPUT_PARAM_ILLEGAL_SIGN_NOT_SUPPORTED;
            translationView.ocrOnError(DefaultObservable.create(error));
            return;
        }
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

    public void zipFile(TranslationView mTranslationView, EventBase eventBase, Context context) {
        File imageFile = null;
        if (TextUtils.isEmpty(eventBase.getArg2())) {
            if (eventBase.getArg0() != 1) {
                return;
            }
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
                        ocrBitmap(mTranslationView,
                                FunctionUtils.readBitmapFromFile(file.getAbsolutePath(), 768));
                    }
                })
                //启动压缩
                .launch();
    }

    public Function<OCRResult, String> setOcrResult() {
        return result -> {
            List<Region> regions = result.getRegions();
            StringBuilder sb = new StringBuilder();
            sb.append("识别结果:" + (result.getErrorCode() == 0 ? "成功" : "识别异常"));
            sb.append("\n");
            for (Region region : regions) {
                List<Line> lines = region.getLines();
                for (Line line : lines) {
                    List<Word> words = line.getWords();
                    for (Word word : words) {
                        sb.append(word.getText()).append(" ");
                    }
                    sb.append("\n");
                }
                sb.append("\n");
            }
            return sb.toString();
        };
    }

    public Function<Translate, Tanslaterecord> settranslateResult() {
        return translate -> {
            List<String> list = new ArrayList<>();
            Logger.e("getWebExplains = "
                     + translate.getWebExplains()
                     + "\ngetTranslations = "
                     + translate.getTranslations()
                     + "\ngetExplains = "
                     + translate.getExplains()
                     + "\ngetUsPhonetic = "
                     + translate.getUsPhonetic()
                     + "\ngetUkPhonetic = "
                     + translate.getUkPhonetic()
                     + "\ngetTo = "
                     + translate.getTo()
                     + "\ngetQuery = "
                     + translate.getQuery()
                     + "\ngetPhonetic = "
                     + translate.getPhonetic()
                     + "\ngetLe = "
                     + translate.getLe()
                     + "\ngetFrom = "
                     + translate.getFrom()
                     + "\ngetDictWebUrl = "
                     + translate.getDictWebUrl()
                     + "\ngetDeeplink = "
                     + translate.getDeeplink()
                     + "\ngetDictDeeplink = "
                     + translate.getDictDeeplink()
                     + "\ngetErrorCode = "
                     + translate.getErrorCode());

            if (translate.getTranslations() == null || translate.getTranslations().size() == 0) {
                list.add("翻译失败");
            } else {
                list.add(ListUtils.listStrToString(translate.getTranslations(), ","));
            }
            if (translate.getExplains() == null || translate.getExplains().size() == 0) {
                list.add("");
            } else {
                list.add(ListUtils.listStrToString(translate.getExplains(), "\n"));
            }
            list.add(translate.getTo());
            list.add(translate.getFrom());
            list.add(translate.getQuery());
            Tanslaterecord currentTranslate = Dbservice.getInstance()
                    .defaultDbConfig()
                    .getCurrentTranslateAsExecute(list.get(0), list.get(2), list.get(3),
                            list.get(4));
            if (currentTranslate == null) {
                currentTranslate = Tanslaterecord.newTanslaterecord(UUID.randomUUID().toString(),
                        list.get(0), list.get(1), translate.getUsPhonetic(),
                        translate.getUkPhonetic(), translate.getTo(), 0, translate.getErrorCode(),
                        translate.getDictDeeplink(), translate.getDeeplink(),
                        translate.getDictWebUrl(), translate.getFrom(), translate.getLe(),
                        translate.getPhonetic(), translate.getQuery());
                PutResult putResult = Dbservice.getInstance()
                        .defaultDbConfig()
                        .insertTanslateRecord(currentTranslate);
                if (putResult.wasInserted()) {
                    Logger.e("插入成功");
                } else {
                    Logger.e("插入失败");
                }
            }
            return currentTranslate;
        };
    }

    public void updateStar(TranslationView mTranslationView, Tanslaterecord tanslate) {
        Dbservice.getInstance()
                .defaultDbConfig()
                .getCurrentTranslateAsRx(tanslate.getTranslations(),
                        tanslate.getCto(), tanslate.getMfrom(), tanslate.getMquery())
                .map(tanslaterecordOptional -> {
                    Tanslaterecord tanslaterecord = tanslaterecordOptional.get();
                    PutResult putResult = null;
                    if (tanslaterecordOptional.get() != null) {
                        if (tanslaterecord.getStart() == 0) {
                            tanslaterecord.setStart(1);
                        } else {
                            tanslaterecord.setStart(0);
                        }
                        putResult = Dbservice.getInstance()
                                .defaultDbConfig()
                                .insertTanslateRecord(tanslaterecord);
                    }
                    if (putResult == null || !putResult.wasUpdated()) {
                        return EventBase.builder().arg0(tanslaterecord.getStart()).build();
                    } else {
                        return EventBase.builder().arg0(tanslaterecord.getStart()).build();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventBase -> {
                    mTranslationView.starSuccess(eventBase);
                }, throwable -> {
                    mTranslationView.starFailure(throwable);
                });
    }
    public Flowable<Changes> loadTranslateRecord() {
        Set<String> tables = new HashSet<>();
        tables.add(Tanslaterecord.__TABLE__);
        return Dbservice.getInstance()
                .defaultDbConfig()
                .getStorIOSQLite()
                .observeChangesInTables(tables, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .startWith(Changes.newInstance(""));
    }

    public Flowable<List<Tanslaterecord>> getAllTranslateRecord() {
        return Dbservice.getInstance()
                .defaultDbConfig()
                .getAllTranslateRecord()
                .map(tanslaterecords -> {
                    List<Tanslaterecord> list = new ArrayList<>();
                    for (int i = tanslaterecords.size() - 1; i >= 0; i--) {
                        list.add(tanslaterecords.get(i));
                    }
                    return list;
                });
    }
}

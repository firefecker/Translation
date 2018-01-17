package com.fire.translation.mvp.view;

import android.view.View;
import android.view.animation.Animation;
import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.translation.db.entities.Tanslaterecord;
import com.fire.translation.widget.EventBase;
import com.pushtorefresh.storio3.sqlite.Changes;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
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

    void rxBus(Observable<EventBase> observable);

    void starFailure(Throwable throwable);

    void starSuccess(EventBase putResult);

    void loadTranslateRecord(Flowable<Changes> changesFlowable);

    void getAllTranslateRecord(Flowable<List<Tanslaterecord>> allTranslateRecord);
}

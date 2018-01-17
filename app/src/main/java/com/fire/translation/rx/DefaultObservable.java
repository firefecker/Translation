package com.fire.translation.rx;

import com.youdao.ocr.online.OCRResult;
import io.reactivex.Observable;

/**
 * Created by fire on 2018/1/16.
 * Date：2018/1/16
 * Author: fire
 * Description:
 */

public class DefaultObservable {

    public static <T>Observable<T> create(T t) {
        return Observable.create(
                e -> {
                    if (t != null) {
                        e.onNext(t);
                        e.onComplete();
                    } else {
                        e.onError(new NullPointerException("result is null"));
                    }
                });
    }

}

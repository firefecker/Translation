package com.fire.translation.rx;

import io.reactivex.Observable;

/**
 *
 * @author fire
 * @date 2018/1/16
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

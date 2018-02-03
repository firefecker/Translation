package com.fire.baselibrary.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author fire
 * @date 2018/1/15
 * Description:
 */

public class DefaultButtonTransformer<T> implements ObservableTransformer<T, T> {
    /**
     * @param upstream the upstream Observable instance
     * @return the transformed ObservableSource instance
     */
    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.throttleFirst(500, TimeUnit.MILLISECONDS);
    }

    public static <T> DefaultButtonTransformer<T> create() {
        return new DefaultButtonTransformer<>();
    }
}

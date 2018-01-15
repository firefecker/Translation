package com.fire.translation.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.util.concurrent.TimeUnit;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */

public class DefaultButtonTransformer<T> implements ObservableTransformer<T, T> {
    /**
     * Applies a function to the upstream Observable and returns an ObservableSource with
     * optionally different element type.
     *
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

package com.fire.translation.rx;

import android.annotation.SuppressLint;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import com.jakewharton.rxbinding2.internal.Functions;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import io.reactivex.Observable;
import java.util.concurrent.Callable;

import static com.jakewharton.rxbinding2.internal.Preconditions.checkNotNull;

/**
 * Created by fire on 2018/1/17.
 * Date：2018/1/17
 * Author: fire
 * Description:
 */
@SuppressLint("RestrictedApi")
public final class RxRvAdapterView {

    @CheckResult
    @NonNull
    public static <T> Observable<Integer> itemClicks(
            @NonNull RecyclerArrayAdapter<T> adapter) {
        checkNotNull(adapter, "adapter == null");
        return new RvAdapterItemClickObservable(adapter);
    }

    @CheckResult
    @NonNull
    public static Observable<Integer> pageChanges(
            @NonNull ViewPager viewPager) {
        checkNotNull(viewPager, "adapter == null");
        return new ViewPagerChangeObservable(viewPager);
    }

    @CheckResult
    @NonNull
    public static <T> Observable<Integer> itemLongClicks(
            @NonNull RecyclerArrayAdapter<T> adapter, @NonNull Callable<Boolean> handled) {
        checkNotNull(adapter, "adapter == null");
        return new RvAdapterItemLongClickObservable(adapter,handled);
    }

    @CheckResult
    @NonNull
    public static <T> Observable<Integer> itemLongClicks(
            @NonNull RecyclerArrayAdapter<T> adapter) {
        checkNotNull(adapter, "adapter == null");
        return itemLongClicks(adapter, Functions.CALLABLE_ALWAYS_TRUE);
    }

}

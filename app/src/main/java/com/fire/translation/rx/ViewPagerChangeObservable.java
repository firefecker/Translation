package com.fire.translation.rx;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import static com.jakewharton.rxbinding2.internal.Preconditions.checkMainThread;

/**
 *
 * @author fire
 * @date 2018/1/18
 * Description:
 */

public class ViewPagerChangeObservable extends Observable<Integer> {
    private final ViewPager view;

    ViewPagerChangeObservable(ViewPager view) {
        this.view = view;
    }


    @SuppressLint("RestrictedApi")
    @Override protected void subscribeActual(Observer<? super Integer> observer) {
        if (!checkMainThread(observer)) {
            return;
        }
        ViewPagerChangeObservable.Listener listener = new ViewPagerChangeObservable.Listener(view, observer);
        observer.onSubscribe(listener);
        view.addOnPageChangeListener(listener);
    }

    static final class Listener<T> extends MainThreadDisposable
            implements ViewPager.OnPageChangeListener {
        private final ViewPager view;
        private final Observer<? super Integer> observer;

        Listener(ViewPager view, Observer<? super Integer> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override protected void onDispose() {
            view.addOnPageChangeListener(null);
        }


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (!isDisposed()) {
                observer.onNext(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}

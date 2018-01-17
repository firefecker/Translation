package com.fire.translation.rx;

import android.annotation.SuppressLint;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import java.util.concurrent.Callable;

import static com.jakewharton.rxbinding2.internal.Preconditions.checkMainThread;

/**
 * Created by fire on 2018/1/17.
 * Date：2018/1/17
 * Author: fire
 * Description:
 */
@SuppressLint("RestrictedApi")
public class RvAdapterItemLongClickObservable extends Observable<Integer> {
    private final RecyclerArrayAdapter<?> view;
    private final Callable<Boolean> handled;

    RvAdapterItemLongClickObservable(RecyclerArrayAdapter<?> view, Callable<Boolean> handled) {
        this.view = view;
        this.handled = handled;
    }

    @Override protected void subscribeActual(Observer<? super Integer> observer) {
        if (!checkMainThread(observer)) {
            return;
        }
        RvAdapterItemLongClickObservable.Listener listener = new RvAdapterItemLongClickObservable.Listener(view, observer, handled);
        observer.onSubscribe(listener);
        view.setOnItemLongClickListener(listener);
    }

    static final class Listener extends MainThreadDisposable implements
            RecyclerArrayAdapter.OnItemLongClickListener {
        private final RecyclerArrayAdapter<?> view;
        private final Observer<? super Integer> observer;
        private final Callable<Boolean> handled;

        Listener(RecyclerArrayAdapter<?> view, Observer<? super Integer> observer,
                Callable<Boolean> handled) {
            this.view = view;
            this.observer = observer;
            this.handled = handled;
        }

        @Override protected void onDispose() {
            view.setOnItemLongClickListener(null);
        }

        @Override
        public boolean onItemLongClick(int position) {
            if (!isDisposed()) {
                try {
                    if (handled.call()) {
                        observer.onNext(position);
                        return true;
                    }
                } catch (Exception e) {
                    observer.onError(e);
                    dispose();
                }
            }
            return false;
        }
    }
}

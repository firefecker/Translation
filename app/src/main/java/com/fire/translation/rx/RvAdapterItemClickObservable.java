package com.fire.translation.rx;

import android.annotation.SuppressLint;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import static com.jakewharton.rxbinding2.internal.Preconditions.checkMainThread;

/**
 *
 * @author fire
 * @date 2018/1/17
 * Description:
 */
public class RvAdapterItemClickObservable<T> extends Observable<Integer> {
    private final RecyclerArrayAdapter<T> view;

    RvAdapterItemClickObservable(RecyclerArrayAdapter<T> view) {
        this.view = view;
    }


    @SuppressLint("RestrictedApi")
    @Override protected void subscribeActual(Observer<? super Integer> observer) {
        if (!checkMainThread(observer)) {
            return;
        }
        RvAdapterItemClickObservable.Listener listener = new RvAdapterItemClickObservable.Listener(view, observer);
        observer.onSubscribe(listener);
        view.setOnItemClickListener(listener);
    }

    static final class Listener<T> extends MainThreadDisposable
            implements RecyclerArrayAdapter.OnItemClickListener {
        private final RecyclerArrayAdapter<T> view;
        private final Observer<? super Integer> observer;

        Listener(RecyclerArrayAdapter<T> view, Observer<? super Integer> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override protected void onDispose() {
            view.setOnItemClickListener(null);
        }

        @Override
        public void onItemClick(int position) {
            if (!isDisposed()) {
                observer.onNext(position);
            }
        }
    }
}

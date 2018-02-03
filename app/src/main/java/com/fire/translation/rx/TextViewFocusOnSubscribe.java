package com.fire.translation.rx;

import android.view.View;
import com.jakewharton.rxbinding2.InitialValueObservable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/**
 *
 * @author fire
 * @date 2018/1/17
 * Description:
 */

public class TextViewFocusOnSubscribe extends InitialValueObservable<Boolean> {
    private final View view;

    TextViewFocusOnSubscribe(View view) {
        this.view = view;
    }

    @Override protected void subscribeListener(Observer<? super Boolean> observer) {
        TextViewFocusOnSubscribe.Listener listener = new TextViewFocusOnSubscribe.Listener(view, observer);
        observer.onSubscribe(listener);
        view.setOnFocusChangeListener(listener);
    }

    @Override protected Boolean getInitialValue() {
        return view.hasFocus();
    }

    static final class Listener extends MainThreadDisposable implements View.OnFocusChangeListener {
        private final View view;
        private final Observer<? super Boolean> observer;

        Listener(View view, Observer<? super Boolean> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override public void onFocusChange(View v, boolean hasFocus) {
            if (!isDisposed()) {
                observer.onNext(hasFocus);
            }
        }

        @Override protected void onDispose() {
            view.setOnFocusChangeListener(null);
        }
    }
}

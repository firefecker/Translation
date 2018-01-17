package com.fire.translation.rx;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/**
 * Created by fire on 2018/1/17.
 * Date：2018/1/17
 * Author: fire
 * Description:
 */

public class TextViewTextOnSubscribe extends InitialValueObservable<Editable> {
    private final TextView view;

    TextViewTextOnSubscribe(TextView view) {
        this.view = view;
    }

    @Override
    protected void subscribeListener(Observer<? super Editable> observer) {
        TextViewTextOnSubscribe.Listener listener = new TextViewTextOnSubscribe.Listener(view, observer);
        observer.onSubscribe(listener);
        view.addTextChangedListener(listener);
    }

    @Override protected Editable getInitialValue() {
        return view.getEditableText();
    }

    final static class Listener extends MainThreadDisposable implements TextWatcher {
        private final TextView view;
        private final Observer<? super Editable> observer;

        Listener(TextView view, Observer<? super Editable> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!isDisposed()) {
                observer.onNext(s);
            }
        }

        @Override
        protected void onDispose() {
            view.removeTextChangedListener(this);
        }
    }
}

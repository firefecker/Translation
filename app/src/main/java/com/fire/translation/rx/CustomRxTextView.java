package com.fire.translation.rx;

import android.annotation.SuppressLint;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.widget.TextView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import static com.jakewharton.rxbinding2.internal.Preconditions.checkNotNull;

/**
 * Created by fire on 2018/1/17.
 * Date：2018/1/17
 * Author: fire
 * Description:
 */
@SuppressLint("RestrictedApi")
public class CustomRxTextView {


    @CheckResult
    @NonNull
    public static InitialValueObservable<Editable> textChanges(@NonNull TextView view) {
        checkNotNull(view, "view == null");
        return new TextViewTextOnSubscribe(view);
    }

    @CheckResult
    @NonNull
    public static InitialValueObservable<Boolean> focusChanges(@NonNull TextView view) {
        checkNotNull(view, "view == null");
        return new TextViewFocusOnSubscribe(view);
    }
}

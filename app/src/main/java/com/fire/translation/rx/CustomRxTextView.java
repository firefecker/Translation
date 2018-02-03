package com.fire.translation.rx;

import android.annotation.SuppressLint;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.widget.TextView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import static com.jakewharton.rxbinding2.internal.Preconditions.checkNotNull;

/**
 *
 * @author fire
 * @date 2018/1/17
 * Description:主要针对TextView等文本控件的文件变化监听和焦点改变的功能，项目中暂时没有用到
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

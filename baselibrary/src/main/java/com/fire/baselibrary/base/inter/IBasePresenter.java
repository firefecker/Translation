package com.fire.baselibrary.base.inter;

/**
 * Created by fire on 2018/1/3.
 * Date：2018/1/3
 * Author: fire
 * Description:
 */

public interface IBasePresenter<T> {
    default void rxBus(Class mClass,Class aClass) {
    }

    default void updateStar(T t) {

    }
}

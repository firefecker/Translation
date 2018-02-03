package com.fire.baselibrary.base.inter;

/**
 *
 * @author fire
 * @date 2018/1/3
 * Description:
 */

public interface IBasePresenter<T> {
    default void rxBus(Class mClass,Class aClass) {
    }

    default void updateStar(T t) {

    }
}

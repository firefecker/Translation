package com.fire.baselibrary.base.inter;

import com.fire.baselibrary.rx.EventBase;
import io.reactivex.Observable;

/**
 *
 * @author fire
 * @date 2018/1/3
 * Description:
 */

public interface IBaseView {

    default void showLoadingView() {

    }
    default void dismissLoadingView() {

    }
    default void notifyError(Throwable e) {

    }

    default void rxBus(Observable<EventBase> observable) {

    }

}

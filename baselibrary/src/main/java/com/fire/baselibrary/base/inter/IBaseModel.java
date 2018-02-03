package com.fire.baselibrary.base.inter;

import com.fire.baselibrary.rx.EventBase;
import com.fire.baselibrary.rx.RxBus;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 *
 * @author fire
 * @date 2018/1/3
 * Description:
 */

public interface IBaseModel {

    default Observable<EventBase> rxBus(Class mClass) {
        return RxBus.getDefault()
                .toObservable(mClass)
                .observeOn(AndroidSchedulers.mainThread());
    }

}

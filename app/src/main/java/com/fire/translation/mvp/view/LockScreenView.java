package com.fire.translation.mvp.view;

import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.translation.db.entities.DailyEntity;
import com.pushtorefresh.storio3.Optional;
import io.reactivex.Flowable;

/**
 * Created by fire on 2018/1/29.
 * Date：2018/1/29
 * Author: fire
 * Description:
 */

public interface LockScreenView extends IBaseView {
    void localData(Flowable<Optional<DailyEntity>> localData);
}

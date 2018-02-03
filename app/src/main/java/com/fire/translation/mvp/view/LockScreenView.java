package com.fire.translation.mvp.view;

import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.translation.db.entities.DailyEntity;
import com.pushtorefresh.storio3.Optional;
import io.reactivex.Flowable;

/**
 *
 * @author fire
 * @date 2018/1/29
 * Description:
 */

public interface LockScreenView extends IBaseView {
    void localData(Flowable<Optional<DailyEntity>> localData);
}

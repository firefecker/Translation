package com.fire.translation.mvp.model;

import com.fire.baselibrary.base.inter.IBaseModel;
import com.fire.translation.db.Dbservice;
import com.fire.translation.db.entities.DailyEntity;
import com.pushtorefresh.storio3.Optional;
import io.reactivex.Flowable;

/**
 *
 * @author fire
 * @date 2018/1/29
 * Description:
 */

public class LockScreenModel implements IBaseModel {
    public Flowable<Optional<DailyEntity>> getLocalData() {
        return Dbservice.getInstance()
                .defaultDbConfig()
                .getCurrentDailyEntity();
    }
}

package com.fire.translation.mvp.view;

import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.translation.db.entities.DailyEntity;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import io.reactivex.Observable;

/**
 * Created by fire on 2018/1/29.
 * Date：2018/1/29
 * Author: fire
 * Description:
 */

public interface SplashView extends IBaseView {
    void loadData(Observable<PutResult> dsapi);
}

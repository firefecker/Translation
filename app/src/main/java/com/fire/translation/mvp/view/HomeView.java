package com.fire.translation.mvp.view;

import com.fire.baselibrary.base.inter.IBaseView;
import com.pushtorefresh.storio3.sqlite.Changes;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import io.reactivex.Flowable;

/**
 *
 * @author fire
 * @date 2018/1/3
 * Description:
 */

public interface HomeView extends IBaseView {

    /**
     *
     */
    //void setData(DailyEntity test);

    void setRecord(Flowable<Changes> listFlowable);

    void updateJsnum(Flowable<PutResult> putResultFlowable);
}

package com.fire.translation.mvp.view;

import android.support.design.widget.BottomNavigationView;
import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.translation.db.entities.TableName;
import com.fire.translation.entity.DailyEntity;
import com.pushtorefresh.storio3.sqlite.Changes;
import io.reactivex.Flowable;
import java.util.List;

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
}

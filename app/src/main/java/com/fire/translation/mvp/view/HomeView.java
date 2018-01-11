package com.fire.translation.mvp.view;

import android.support.design.widget.BottomNavigationView;
import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.translation.entity.DailyEntity;

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
    void setData(DailyEntity test);
}

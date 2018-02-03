package com.fire.translation.mvp.view;

import com.fire.baselibrary.base.inter.IBaseView;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import io.reactivex.Observable;

/**
 *
 * @author fire
 * @date 2018/1/29
 * Description:
 */

public interface SplashView extends IBaseView {
    void loadData(Observable<PutResult> dsapi);
}

package com.fire.translation.mvp.presenter;

import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.translation.mvp.model.HomeModel;
import com.fire.translation.mvp.view.HomeView;

/**
 * Description:
 *
 * @author fire
 */

public class HomePresenter implements IBasePresenter {

    private HomeModel mHomeModel;
    private HomeView mHomeView;

    public HomePresenter(HomeView mHomeView) {
        this.mHomeView = mHomeView;
        this.mHomeModel = new HomeModel();
    }

    public void getDsapi(String date,boolean isShowLoadingView) {
        if (isShowLoadingView) {
            mHomeView.showLoadingView();
        }
        mHomeModel.getDsapi(date)
        .subscribe(test -> {
            mHomeView.setData(test);
            mHomeView.dismissLoadingView();
        },
        throwable -> {
            mHomeView.notifyError(throwable);
        });
    }
}

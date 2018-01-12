package com.fire.translation.mvp.presenter;

import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.translation.mvp.model.HomeModel;
import com.fire.translation.mvp.model.MainModel;
import com.fire.translation.mvp.view.HomeView;
import com.fire.translation.mvp.view.MainView;

/**
 * Created by fire on 2018/1/12.
 * Date：2018/1/12
 * Author: fire
 * Description:
 */

public class MainPresenter implements IBasePresenter {

    private MainModel mMainModel;
    private MainView mMainView;

    public MainPresenter(MainView mHomeView) {
        this.mMainView = mHomeView;
        this.mMainModel = new MainModel();
    }

    public void loadExistTable() {
        mMainView.loadExistTableName(mMainModel.getExistTableName());
    }

    public void setTableStatus() {
        mMainView.loadStatus(mMainModel.setExistTableStatus());
    }
}

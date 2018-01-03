package com.fire.translation.ui.fragment;

import android.support.annotation.NonNull;
import android.view.MenuItem;
import com.fire.baselibrary.base.BaseFragment;
import com.fire.translation.R;
import com.fire.translation.entity.DailyEntity;
import com.fire.translation.mvp.presenter.HomePresenter;
import com.fire.translation.mvp.view.HomeView;

/**
 *
 * @author fire
 * @date 2018/1/3
 * Description:
 */

public class HomeFragment extends BaseFragment implements HomeView {

    private HomePresenter mHomePresenter;

    @Override
    public int resourceId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        mHomePresenter = new HomePresenter(this);
        mHomePresenter.getDsapi("2017-12-26", true);
    }

    @Override
    public void setData(DailyEntity test) {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void dismissLoadingView() {

    }

    @Override
    public void notifyError(Throwable e) {

    }
}

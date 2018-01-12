package com.fire.translation.ui.activity;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.BindView;
import com.fire.translation.R;
import com.fire.baselibrary.base.BaseActivity;
import com.fire.translation.db.entities.TableName;
import com.fire.translation.mvp.presenter.MainPresenter;
import com.fire.translation.mvp.view.MainView;
import com.fire.translation.ui.fragment.DashboardFragment;
import com.fire.translation.ui.fragment.HomeFragment;
import com.fire.translation.ui.fragment.MineFragment;
import com.orhanobut.logger.Logger;
import com.pushtorefresh.storio3.Optional;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author fire
 */
public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;
    @BindView(R.id.layout_loading)
    RelativeLayout mLayoutLoading;
    @BindView(R.id.fab)
    FloatingActionButton mActionButton;

    private HomeFragment mHomeFragment;
    private DashboardFragment mDashboardFragment;
    private MineFragment mMineFragment;

    private MainPresenter mMainPresenter;

    @Override
    public int getLayout() {

        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mMainPresenter = new MainPresenter(this);
        mMainPresenter.loadExistTable();
        mHomeFragment = new HomeFragment();
        mDashboardFragment = new DashboardFragment();
        mMineFragment = new MineFragment();
        mNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            updateShowSatus(true, mDashboardFragment, mMineFragment);
                            return true;
                        case R.id.navigation_dashboard:
                            updateShowSatus(false, mDashboardFragment, mMineFragment);
                            return true;
                        case R.id.navigation_notifications:
                            updateShowSatus(false, mMineFragment, mDashboardFragment);
                            return true;
                        default:
                            return false;
                    }
                });
    }

    private void updateShowSatus(boolean isHome, Fragment fragment1, Fragment fragment2) {
        if (isHome) {
            mActionButton.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .hide(fragment1)
                    .hide(fragment2)
                    .show(mHomeFragment)
                    .commit();
        } else {
            mActionButton.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction()
                    .hide(mHomeFragment)
                    .hide(fragment2)
                    .show(fragment1)
                    .commit();
        }
    }

    @Override
    public void initData() {
        mActionButton.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_frame, mHomeFragment)
                .add(R.id.layout_frame, mDashboardFragment)
                .add(R.id.layout_frame, mMineFragment)
                .hide(mDashboardFragment)
                .hide(mMineFragment)
                .show(mHomeFragment)
                .commit();
    }

    @Override
    public void loadExistTableName(Flowable<Optional<TableName>> existTableName) {
        existTableName.compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(tableNameOptional -> {
                    if (tableNameOptional == null || tableNameOptional.get() == null) {
                        Logger.e("null");
                        mMainPresenter.setTableStatus();
                    } else {
                        Logger.e("no null");
                    }
                },throwable -> {
                    Logger.e(throwable.toString());
                    mMainPresenter.setTableStatus();
                });
    }

    @Override
    public void loadStatus(Observable<PutResult> putResultObservable) {
        putResultObservable .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(putResult -> {
                    if (putResult.wasUpdated()) {

                    }
                }, throwable -> {
                    Logger.e(throwable.toString());
                });
    }
}

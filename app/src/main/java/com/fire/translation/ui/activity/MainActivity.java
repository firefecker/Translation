package com.fire.translation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.BindView;
import com.fire.translation.R;
import com.fire.baselibrary.base.BaseActivity;
import com.fire.translation.constant.Constant;
import com.fire.translation.db.entities.TableName;
import com.fire.translation.mvp.presenter.MainPresenter;
import com.fire.translation.mvp.view.MainView;
import com.fire.baselibrary.rx.DefaultButtonTransformer;
import com.fire.baselibrary.rx.RxBus;
import com.fire.translation.ui.fragment.DashboardFragment;
import com.fire.translation.ui.fragment.HomeFragment;
import com.fire.translation.ui.fragment.MineFragment;
import com.fire.translation.ui.fragment.TranslationFragment;
import com.fire.translation.utils.FunctionUtils;
import com.fire.baselibrary.rx.EventBase;
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
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tablayout)
    public TabLayout mTabLayout;

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

        mMainPresenter.loadExistTable();
        mHomeFragment = new HomeFragment();
        mDashboardFragment = new DashboardFragment();
        mMineFragment = new MineFragment();

        mNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            mToolbar.setTitle(R.string.title_home);
                            mTabLayout.setVisibility(View.GONE);
                            updateShowSatus(true, mDashboardFragment, mMineFragment);
                            return true;
                        case R.id.navigation_dashboard:
                            mToolbar.setTitle("");
                            mTabLayout.setVisibility(View.VISIBLE);
                            updateShowSatus(false, mDashboardFragment, mMineFragment);
                            return true;
                        case R.id.navigation_notifications:
                            mToolbar.setTitle(R.string.title_notifications);
                            mTabLayout.setVisibility(View.GONE);
                            updateShowSatus(false, mMineFragment, mDashboardFragment);
                            return true;
                        default:
                            return false;
                    }
                });
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle paramBundle) {
        super.onActivityCreate(paramBundle);
        mMainPresenter = new MainPresenter(this);
        requestPermission(Constant.PERMISSIONS)
                .compose(DefaultButtonTransformer.create())
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission.granted) {
                        Logger.e("权限申请成功");
                    } else {
                        Logger.e("权限申请失败或者已经申请过权限了");
                    }
                });
    }

    public void setIndicator() {
        mTabLayout.post(() -> FunctionUtils.setIndicator(mTabLayout, 10, 10));
    }

    private void updateShowSatus(boolean isHome, Fragment fragment1, Fragment fragment2) {
        if (isHome) {
            mActionButton.setVisibility(View.GONE);
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
        supportInvalidateOptionsMenu();
    }

    @Override
    public void initData() {
        mToolbar.setTitle(R.string.title_home);
        mTabLayout.setVisibility(View.GONE);
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
                        mMainPresenter.setTableStatus();
                    }
                }, throwable -> {
                    mMainPresenter.setTableStatus();
                });
    }

    @Override
    public void loadStatus(Observable<PutResult> putResultObservable) {
        putResultObservable.compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(putResult -> {
                    if (putResult.wasUpdated()) {

                    }
                }, throwable -> {
                    Logger.e(throwable.toString());
                });
    }

    @Override
    public void loadPath(String s) {
        RxBus.getDefault().post(EventBase.builder().arg2(s).receiver(
                TranslationFragment.class).build());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.ACTION_IMAGE:
                if (resultCode == RESULT_OK) {
                    RxBus.getDefault().post(EventBase.builder().arg0(1).receiver(
                            TranslationFragment.class).build());
                }
                break;
            case Constant.ACTION_ALBUM:
                mMainPresenter.loadPath(this,data);
                break;
        }
    }
}

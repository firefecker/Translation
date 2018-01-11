package com.fire.translation.ui.activity;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.BindView;
import com.fire.translation.R;
import com.fire.baselibrary.base.BaseActivity;
import com.fire.translation.ui.fragment.DashboardFragment;
import com.fire.translation.ui.fragment.HomeFragment;
import com.fire.translation.ui.fragment.MineFragment;

/**
 * @author fire
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;
    @BindView(R.id.layout_loading)
    RelativeLayout mLayoutLoading;
    @BindView(R.id.fab)
    FloatingActionButton mActionButton;

    private HomeFragment mHomeFragment;
    private DashboardFragment mDashboardFragment;
    private MineFragment mMineFragment;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
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
}

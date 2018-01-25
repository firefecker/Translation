package com.fire.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.fire.baselibrary.R;
import com.fire.baselibrary.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observable;

/**
 * @author fire
 * @date 2017/12/28
 * Description:
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    private Unbinder mBind;
    private RxPermissions mRxPermissions;

    /**
     * 退出时间
     */
    private static long currentBackPressedTime = 0;

    public abstract @LayoutRes int getLayout();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mBind = ButterKnife.bind(this);
        onActivityCreate(savedInstanceState);
        initView();
        initData();
    }

    private RxPermissions getRxPermission() {
        if (mRxPermissions == null) {
            mRxPermissions = new RxPermissions(this);
        }
        return mRxPermissions;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();//解除绑定
    }

    /**
     * 实现父类的onActivityCreate，完成Presenter的生成以及P和V的绑定
     * @param paramBundle
     */
    protected void onActivityCreate(@Nullable Bundle paramBundle) {

    }

    public Observable<Permission> requestPermission(String... permission ) {
        return getRxPermission().requestEach(permission);
    }

    public void setToolBar(Toolbar toolbar , String title ) {
        toolbar.setTitle(title + "");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void setToolBarNoBack(Toolbar toolbar , String title ) {
        toolbar.setTitle(title + "");
        setSupportActionBar(toolbar);
    }

    /**
     * 退出应用 true 完全退出 false 结束当前页面
     */
    protected void exitSystem(boolean isExitSystem) {
        if (System.currentTimeMillis() - currentBackPressedTime > 2000) {
            currentBackPressedTime = System.currentTimeMillis();
            ToastUtils.showToast(R.string.one_more_click_exit_str);
        } else {
            if (isExitSystem) {
                compeletlyExitSystem();
            } else {
                super.onBackPressed();
            }
        }
    }

    /**
     * 完全退出应用
     */
    protected void compeletlyExitSystem() {
        // 退出
        super.onBackPressed();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}

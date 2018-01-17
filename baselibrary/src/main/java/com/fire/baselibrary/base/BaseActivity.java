package com.fire.baselibrary.base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author fire
 * @date 2017/12/28
 * Description:
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    private Unbinder mBind;
    private RxPermissions mRxPermissions;

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

}

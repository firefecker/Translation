package com.fire.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author fire
 * @date 2017/12/28
 * Description:
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    private Unbinder mBind;

    public abstract @LayoutRes int getLayout();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mBind = ButterKnife.bind(this);
        initView();
        initData();
        onActivityCreate(savedInstanceState);
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
}

package com.fire.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author fire
 * @date 2017/12/28
 * Description:
 */

public abstract class BaseActivity extends RxAppCompatActivity {


    public abstract @LayoutRes int getLayout();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        initData();
    }
}

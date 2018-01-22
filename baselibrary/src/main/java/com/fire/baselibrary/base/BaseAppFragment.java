package com.fire.baselibrary.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.trello.rxlifecycle2.components.RxFragment;

/**
 *
 * @author fire
 * @date 2018/1/3
 * Description:
 */

public abstract class BaseAppFragment extends RxFragment {

    public Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        View inflate = inflater.inflate(resourceId(), container, false);
        ButterKnife.bind(this,inflate);
        onFragmentCreate(savedInstanceState);
        initView();
        return inflate;
    }
    public abstract @LayoutRes int resourceId ();

    public abstract void initView ();

    /**
     * 实现父类的onActivityCreate，完成Presenter的生成以及P和V的绑定
     * @param paramBundle
     */
    protected void onFragmentCreate(@Nullable Bundle paramBundle) {

    }
}

package com.fire.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 *
 * @author fire
 * @date 2018/1/3
 * Description:
 */

public abstract class BaseFragment extends RxFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View inflate = inflater.inflate(resourceId(), container, false);
        ButterKnife.bind(this,inflate);
        initView();
        return inflate;
    }
    public abstract @LayoutRes int resourceId ();

    public abstract void initView ();
}

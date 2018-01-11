package com.fire.translation.ui.fragment;

import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import com.fire.baselibrary.base.BaseFragment;
import com.fire.translation.R;
import com.fire.translation.entity.DailyEntity;
import com.fire.translation.mvp.presenter.HomePresenter;
import com.fire.translation.mvp.view.HomeView;
import com.fire.translation.view.NotifyTextView;

/**
 *
 * @author fire
 * @date 2018/1/3
 * Description:
 */

public class HomeFragment extends BaseFragment implements HomeView {

    @BindView(R.id.nv_jcnum)
    NotifyTextView mNvJcnum;
    @BindView(R.id.nv_jsnum)
    NotifyTextView mNvJsnum;
    @BindView(R.id.nv_review)
    NotifyTextView mNvReview;
    @BindView(R.id.nvtype)
    NotifyTextView mNvType;
    @BindView(R.id.nvzwnum)
    NotifyTextView mNvZwnum;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.tv_type)
    TextView mTvType;

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

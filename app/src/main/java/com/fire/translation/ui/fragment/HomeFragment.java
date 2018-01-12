package com.fire.translation.ui.fragment;

import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import com.fire.baselibrary.base.BaseFragment;
import com.fire.translation.R;
import com.fire.translation.mvp.presenter.HomePresenter;
import com.fire.translation.mvp.view.HomeView;
import com.fire.translation.view.NotifyTextView;
import com.orhanobut.logger.Logger;
import com.pushtorefresh.storio3.sqlite.Changes;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
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
        //mHomePresenter.getDsapi("2017-12-26", true);
        mHomePresenter.loadRecord();
    }

    //@Override
    //public void setData(DailyEntity test) {
    //    Logger.e(test.toString());
    //}

    @Override
    public void setRecord(Flowable<Changes> listFlowable) {
        listFlowable.compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .map(changes -> mHomePresenter.getRecord())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(record -> {
                    mNvJcnum.setLeftText(record.getReview() + "");
                    mNvJsnum.setLeftText(record.getRecordTime() + "");
                    mNvReview.setLeftText(record.getRecordDays() + "");
                    mNvType.setLeftText(record.getRecordCount() + "");
                    mNvZwnum.setLeftText(record.getRecordWords() + "");
                },throwable -> {
                    Logger.e(throwable.toString());
                });
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

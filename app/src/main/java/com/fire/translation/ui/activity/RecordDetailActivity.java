package com.fire.translation.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import com.fire.baselibrary.base.BaseActivity;
import com.fire.baselibrary.rx.EventBase;
import com.fire.baselibrary.rx.RxBus;
import com.fire.baselibrary.utils.ToastUtils;
import com.fire.translation.R;
import com.fire.translation.adapter.RecordDetailAdapter;
import com.fire.translation.db.entities.Tanslaterecord;
import com.fire.translation.mvp.presenter.RecordDetailPresenter;
import com.fire.translation.mvp.view.RecordDetailView;
import com.fire.translation.ui.fragment.TranslationFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.orhanobut.logger.Logger;
import com.pushtorefresh.storio3.sqlite.Changes;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResult;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * @author fire
 * @date 2018/1/25
 * Description:
 */

@SuppressLint("Registered")
public class RecordDetailActivity extends BaseActivity implements RecordDetailView {

    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private RecordDetailPresenter mDetailPresenter;
    private RecordDetailAdapter mTranslateRecordAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_recorddetail;
    }

    @Override
    public void initView() {

        setToolBar(mToolbar, getString(R.string.detail));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTranslateRecordAdapter = new RecordDetailAdapter(this, mDetailPresenter);
        mRecyclerView.setAdapter(mTranslateRecordAdapter);
    }

    @Override
    public void initData() {
        mDetailPresenter.loadTranslateRecord();
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle paramBundle) {
        super.onActivityCreate(paramBundle);
        mDetailPresenter = new RecordDetailPresenter(this);
    }

    @Override
    public void loadTranslateRecord(Flowable<Changes> changesFlowable) {
        changesFlowable.compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(changes -> mDetailPresenter.getAllTranslateRecord());
    }

    @Override
    public void getAllTranslateRecord(Flowable<List<Tanslaterecord>> allTranslateRecord) {
        allTranslateRecord.compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mTranslateRecordAdapter.asLoadAction(),
                        throwable -> Logger.e(throwable.toString()));
    }

    @Override
    public void starFailure(Throwable throwable) {
        ToastUtils.showToast(throwable.getMessage());
    }

    @Override
    public void starSuccess(EventBase eventBase) {
        RxBus.getDefault().postEventBase(eventBase);
        mDetailPresenter.getAllTranslateRecord();
    }

    @Override
    public void deleteRecord(Flowable<DeleteResult> deleteResultFlowable) {
        mDetailPresenter.loadTranslateRecord();
        deleteResultFlowable.compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(deleteResult -> RxBus.getDefault()
                                .postEventBase(EventBase.builder().receiver(
                                        TranslationFragment.class).arg0(R.string.notify).build()),
                        throwable -> Logger.e(throwable.toString()));
    }
}

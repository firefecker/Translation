package com.fire.translation.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.Switch;
import butterknife.BindView;
import com.fire.baselibrary.base.BaseActivity;
import com.fire.baselibrary.utils.ToastUtils;
import com.fire.translation.R;
import com.fire.translation.adapter.ReviewAdapter;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.Word;
import com.fire.translation.mvp.presenter.ReviewPresenter;
import com.fire.translation.mvp.view.ReviewView;
import com.fire.baselibrary.rx.RxBus;
import com.fire.translation.rx.RxRvAdapterView;
import com.fire.translation.ui.fragment.HomeFragment;
import com.fire.baselibrary.rx.EventBase;
import com.iflytek.cloud.SpeechSynthesizer;
import com.orhanobut.logger.Logger;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fire
 * @date 2018/1/18
 * Description:
 */

public class ReviewActivity extends BaseActivity implements ReviewView {

    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ReviewPresenter mReviewPresenter;

    private List<Word> mList = new ArrayList<>();
    private Record mRecord;
    private ReviewAdapter mReviewAdapter;
    private SpeechSynthesizer mTts;

    @Override
    public int getLayout() {
        mRecord = (Record) getIntent().getSerializableExtra("data");
        return R.layout.activity_review;
    }

    @Override
    public void initView() {
        setToolBar(mToolbar,"单词复习");
        mTts = mReviewPresenter.setParam(this);
        mReviewAdapter = new ReviewAdapter(mList,mReviewPresenter);
        mViewPager.setAdapter(mReviewAdapter);
       RxRvAdapterView.pageChanges(mViewPager)
               .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(integer -> {
                   mToolbar.setTitle(
                           String.format("单词复习%d/%d", integer + 1, mReviewAdapter.getSize()));
               }, throwable -> Logger.e(throwable.toString()));
    }

    @Override
    public void initData() {
        mReviewPresenter.loadData(mRecord.getReview());
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle paramBundle) {
        mReviewPresenter = new ReviewPresenter(this);
    }

    @Override
    public void loadData(Flowable<List<Word>> listFlowable) {
        listFlowable.compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .map(words -> {
                    mToolbar.setTitle(String.format("单词复习1/%d",words.size()));
                    return words;
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mReviewAdapter.asLoadAction(), throwable -> {
                    Logger.e(throwable.toString());});
    }

    @Override
    public void setUpDateRememberStatus(Switch mSwRemember, Flowable<PutResult> putResultFlowable,boolean status) {
        putResultFlowable.compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(putResult -> {
                    if (putResult.wasUpdated()) {
                        mSwRemember.setChecked(status);
                        if (status) {
                            mRecord.setRecordWords(mRecord.getRecordWords() + 1);
                        } else {
                            mRecord.setRecordWords(mRecord.getRecordWords() - 1);
                        }
                        mReviewPresenter.updateRecordWords(mRecord);
                    } else {
                        ToastUtils.showToast("添加失败");
                    }
                });
    }

    @Override
    public void updateRecordWords(Flowable<PutResult> putResultFlowable) {
        putResultFlowable.compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(putResult -> {
                    if (putResult.wasUpdated()) {
                        RxBus.getDefault()
                                .postEventBase(EventBase.builder()
                                        .arg0(0)
                                        .receiver(HomeFragment.class)
                                        .build());
                    }
                }, throwable -> Logger.e(throwable.toString()));
    }

    @Override
    public void setUpDateNewwordStatus(CheckBox mCbAdd, Flowable<PutResult> putResultFlowable,
            boolean status) {
        putResultFlowable.compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(putResult -> {
                    if (putResult.wasUpdated()) {
                        mCbAdd.setChecked(status);
                        RxBus.getDefault()
                            .postEventBase(EventBase
                                .builder()
                                .arg0(0)
                                .receiver(HomeFragment.class)
                                .build());
                    } else {
                        ToastUtils.showToast("添加失败");
                    }
                }, throwable -> Logger.e(throwable.toString()));
    }

    @Override
    public void startSpeak(String content) {
        mReviewPresenter.speak(content,mTts,this);
    }

}

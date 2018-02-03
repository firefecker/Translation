package com.fire.translation.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import butterknife.BindView;
import com.fire.baselibrary.base.BaseFragment;
import com.fire.baselibrary.rx.EventBase;
import com.fire.translation.R;
import com.fire.translation.adapter.RecordAdapter;
import com.fire.translation.adapter.StickyHeaderAdapter;
import com.fire.translation.db.entities.Record;
import com.fire.translation.mvp.presenter.RecordPresenter;
import com.fire.translation.mvp.view.RecordView;
import com.fire.translation.utils.DisplayUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.jude.easyrecyclerview.decoration.StickyHeaderDecoration;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.List;

/**
 *
 * @author fire
 * @date 2018/1/15
 * Description:
 */

public class RecordFragment extends BaseFragment implements RecordView {

    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;
    @BindView(R.id.actbtn_top)
    FloatingActionButton mActionButton;

    private RecordPresenter mRecordPresenter;
    private RecordAdapter mRecordAdapter;
    private StickyHeaderAdapter mHeaderAdapter;
    private StickyHeaderDecoration mDecoration;

    @Override
    public int resourceId() {
        return R.layout.fragment_record;
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        DividerDecoration itemDecoration = new DividerDecoration(Color.LTGRAY,
                DisplayUtil.dip2px(mActivity, 0.2f), 0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecordAdapter = new RecordAdapter(mActivity);
        mRecyclerView.setAdapter(mRecordAdapter);
        mActionButton.setOnClickListener(v -> mRecyclerView.scrollToPosition(0));
        mRecordPresenter.loadData();
    }

    @Override
    protected void onFragmentCreate(@Nullable Bundle paramBundle) {
        mRecordPresenter = new RecordPresenter(this);
        mRecordPresenter.rxBus(EventBase.class, getClass());
    }

    @Override
    public void loadData(Flowable<List<Record>> listFlowable) {
        listFlowable.compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .map(mRecordPresenter.sortData())
                .map(words -> {
                    if (mDecoration != null) {
                        mRecyclerView.removeItemDecoration(mDecoration);
                    }
                    mHeaderAdapter = new StickyHeaderAdapter(mActivity, words);
                    mDecoration = new StickyHeaderDecoration(mHeaderAdapter);
                    mDecoration.setIncludeHeader(false);
                    mRecyclerView.addItemDecoration(mDecoration);
                    return words;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mRecordAdapter.asLoadAction(),
                        throwable -> Logger.e(throwable.toString()));
    }

    @Override
    public void rxBus(Observable<EventBase> observable) {
        observable.compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventBase -> {
                    if (eventBase == null) {
                        return;
                    }
                    if (eventBase.getArg0() == 0) {
                        mRecordPresenter.loadData();
                    }
                }, throwable -> Logger.e(throwable.toString()));
    }
}

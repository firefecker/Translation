package com.fire.translation.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.CheckBox;
import butterknife.BindView;
import com.fire.baselibrary.base.BaseFragment;
import com.fire.baselibrary.rx.EventBase;
import com.fire.translation.R;
import com.fire.translation.adapter.WordBookAdapter;
import com.fire.translation.db.entities.Word;
import com.fire.translation.mvp.presenter.WordbookPresenter;
import com.fire.translation.mvp.view.WordbookView;
import com.fire.translation.utils.DisplayUtil;
import com.iflytek.cloud.SpeechSynthesizer;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.List;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */

public class WordbookFragment extends BaseFragment implements WordbookView {

    @BindView(R.id.recyclerview)
    EasyRecyclerView mRecyclerView;

    private WordbookPresenter mWordbookPresenter;
    private  int newWord = 0,remomber = 1;
    private WordBookAdapter mWordBookAdapter;
    private  SpeechSynthesizer mTts;

    @Override
    public int resourceId() {
        return R.layout.fragment_wordbook;
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        DividerDecoration itemDecoration = new DividerDecoration(Color.LTGRAY,
                DisplayUtil.dip2px(mActivity, 0.2f), 0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        mWordBookAdapter = new WordBookAdapter(mActivity,mWordbookPresenter);
        mRecyclerView.setAdapter(mWordBookAdapter);
        mTts = mWordbookPresenter.setParam(mActivity);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_workbook,menu);
        CheckBox mCbRemember = (CheckBox) menu.findItem(R.id.cb_remember).getActionView();
        CheckBox mCbNewWord = (CheckBox) menu.findItem(R.id.cb_newword).getActionView();
        mCbNewWord.setText("生词");
        mCbRemember.setText("掌握");
        mCbNewWord.setButtonDrawable(R.drawable.selector_click_bg);
        mCbRemember.setButtonDrawable(R.drawable.selector_click_bg);
        mCbNewWord.setTextColor(Color.WHITE);
        mCbRemember.setTextColor(Color.WHITE);
        if (remomber == 1) {
            mCbRemember.setChecked(true);
        } else {
            mCbRemember.setChecked(false);
        }
        if (newWord == 1) {
            mCbNewWord.setChecked(true);
        } else {
            mCbNewWord.setChecked(false);
        }
        if (mWordBookAdapter.getAllData().size() == 0) {
            mWordbookPresenter.loadData(newWord,remomber);
        }
        mCbNewWord.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                newWord = 1;
            } else {
                newWord = 0;
            }
            mWordbookPresenter.loadData(newWord,remomber);
        });
        mCbRemember.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                remomber = 1;
            } else {
                remomber = 0;
            }
            mWordbookPresenter.loadData(newWord,remomber);
        });
    }

    @Override
    protected void onFragmentCreate(@Nullable Bundle paramBundle) {
        super.onFragmentCreate(paramBundle);
        mWordbookPresenter = new WordbookPresenter(this);
        mWordbookPresenter.rxBus(EventBase.class,getClass());
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
                        mWordbookPresenter.loadData(newWord,remomber);
                    }
                },throwable -> Logger.e(throwable.toString()));
    }

    @Override
    public void loadData(Flowable<List<Word>> listFlowable) {
        listFlowable.compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mWordBookAdapter.asLoadAction(),
                        throwable -> Logger.e(throwable.toString()));
    }

    @Override
    public void startSpeak(String content) {
        mWordbookPresenter.speak(content,mTts,mActivity);
    }

}

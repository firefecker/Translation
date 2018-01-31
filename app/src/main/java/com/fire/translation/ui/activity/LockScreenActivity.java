package com.fire.translation.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import butterknife.BindView;
import com.fire.baselibrary.base.BaseActivity;
import com.fire.baselibrary.utils.ImageLoader;
import com.fire.translation.R;
import com.fire.translation.db.entities.DailyEntity;
import com.fire.translation.mvp.presenter.LockScreenPresenter;
import com.fire.translation.mvp.view.LockScreenView;
import com.fire.translation.utils.DateUtils;
import com.fire.translation.view.ShimmerTextView;
import com.fire.translation.view.SwipeBackLayout;
import com.fire.translation.widget.Shimmer;
import com.orhanobut.logger.Logger;
import com.pushtorefresh.storio3.Optional;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.Flowable;
import java.util.Date;

/**
 * @author fire
 * @date 2018/1/29
 * Description:
 */

public class LockScreenActivity extends BaseActivity implements LockScreenView {
    @BindView(R.id.lock_time)
    TextClock mTextClock;
    @BindView(R.id.lock_date)
    TextView mLockDate;
    @BindView(R.id.lockmain_shimmer)
    ShimmerTextView mShimmerTextView;
    @BindView(R.id.locksecond_shimmer)
    ShimmerTextView mShimmerTextViews;
    @BindView(R.id.swipeback)
    SwipeBackLayout mSwipeBackLayout;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    private LockScreenPresenter mLockScreenPresenter;

    @Override
    public int getLayout() {
        translateStatubar();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.translate));
        return R.layout.activity_lockscreen;
    }

    @Override
    public void initView() {
        mSwipeBackLayout.setSwipeBackListener(() -> {
            onBackPressed();
        });
    }

    @Override
    public void initData() {
        mLockDate.setText(DateUtils.formatDateToString(new Date(), "yyyy/MM/dd/EEEE"));
        mLockScreenPresenter.getLocalData();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        fitSystem(hasFocus);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int key = event.getKeyCode();
        switch (key) {
            case KeyEvent.KEYCODE_BACK:
                return true;
            case KeyEvent.KEYCODE_MENU:
                return true;
            case KeyEvent.KEYCODE_HOME:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle paramBundle) {
        super.onActivityCreate(paramBundle);
        mLockScreenPresenter = new LockScreenPresenter(this);
    }

    @Override
    public void localData(Flowable<Optional<DailyEntity>> localData) {
        localData.compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .map(dailyEntityOptional -> dailyEntityOptional.get())
                .subscribe(dailyEntity -> {
                    if (dailyEntity == null) {
                        return;
                    }
                    mShimmerTextView.setText(
                            String.format("Original:%s", dailyEntity.getContent()));
                    mShimmerTextViews.setText(String.format("译：%s", dailyEntity.getNote()));
                    Shimmer shimmer = new Shimmer();
                    Shimmer shimmer1 = new Shimmer();
                    shimmer.start(mShimmerTextView);
                    shimmer1.start(mShimmerTextViews);
                    ImageLoader.loadImage(dailyEntity.getPicture2(), R.color.translate).into(mIvBg);
                }, throwable -> Logger.e(throwable.toString()));
    }
}

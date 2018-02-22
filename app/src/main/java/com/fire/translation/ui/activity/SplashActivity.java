package com.fire.translation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.BindView;
import com.fire.baselibrary.base.BaseActivity;
import com.fire.baselibrary.rx.DefaultButtonTransformer;
import com.fire.translation.R;
import com.fire.translation.constant.Constant;
import com.fire.translation.mvp.presenter.SplashPresenter;
import com.fire.translation.mvp.view.SplashView;
import com.fire.translation.utils.DateUtils;
import com.fire.translation.view.CountDownView;
import com.orhanobut.logger.Logger;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.Date;

/**
 * @author fire
 * @date 2018/1/26
 * Description:
 */

public class SplashActivity extends BaseActivity implements SplashView {

    @BindView(R.id.view_countdown)
    CountDownView mCountDownView;
    private SplashPresenter mSplashPresenter;

    @Override
    public int getLayout() {
        translateStatubar();
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        mCountDownView.setOnLoadingFinishListener(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        });

        mCountDownView.setOnClickListener(v -> {
            mCountDownView.removeLoading();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        });
    }

    @Override
    public void initData() {
        mCountDownView.start();
        mSplashPresenter.getDsapi(DateUtils.getFormatDate1(new Date(), DateUtils.dateFormat1),
                false);
    }

    //进入沉浸模式
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        fitSystem(hasFocus);
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle paramBundle) {
        super.onActivityCreate(paramBundle);
        mSplashPresenter = new SplashPresenter(this);
        requestPermission(Constant.PERMISSIONS)
                .compose(DefaultButtonTransformer.create())
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission.granted) {
                        Logger.e("权限申请成功");
                    } else {
                        Logger.e("权限申请失败或者已经申请过权限了");
                    }
                });
    }

    @Override
    public void loadData(Observable<PutResult> dsapi) {
        dsapi.compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(putResult -> Logger.e(putResult.toString()),
                        throwable -> Logger.e(throwable.toString()));
    }
}

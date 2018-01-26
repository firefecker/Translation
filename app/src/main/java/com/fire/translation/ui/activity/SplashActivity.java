package com.fire.translation.ui.activity;

import android.content.Intent;
import butterknife.BindView;
import com.fire.baselibrary.base.BaseActivity;
import com.fire.translation.R;
import com.fire.translation.view.CountDownView;

/**
 * @author fire
 * @date 2018/1/26
 * Date：2018/1/26
 * Author: fire
 * Description:
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.view_countdown)
    CountDownView mCountDownView;

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
    }

    @Override
    public void initData() {
        mCountDownView.start();
    }

    //进入沉浸模式
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        fitSystem(hasFocus);
    }
}

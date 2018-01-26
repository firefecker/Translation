package com.fire.translation.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import butterknife.BindView;
import com.fire.baselibrary.base.BaseActivity;
import com.fire.translation.R;
import com.fire.translation.view.CountDownView;

/**
 *
 * @author fire
 * @date 2018/1/26
 * Date：2018/1/26
 * Author: fire
 * Description:
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.view_countdown)
    CountDownView mCountDownView;


    private View decorView;

    @Override
    public int getLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        mCountDownView.setOnLoadingFinishListener(() -> {
          startActivity(new Intent(SplashActivity.this,MainActivity.class));
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
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            if (null == decorView) {
                decorView = getWindow().getDecorView();
            }
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
            decorView.setOnSystemUiVisibilityChangeListener(
                    visibility -> decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN));
        }
    }
}

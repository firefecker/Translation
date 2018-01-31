package com.fire.translation.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.translation.R;
import com.fire.translation.mvp.model.MainModel;
import com.fire.translation.mvp.view.MainView;

/**
 * Created by fire on 2018/1/12.
 * Date：2018/1/12
 * Author: fire
 * Description:
 */

public class MainPresenter implements IBasePresenter {

    private MainModel mMainModel;
    private MainView mMainView;

    // 声明屏幕的宽高
    float x, y,mTouchStartX,mTouchStartY;
    int top;
    private  WindowManager.LayoutParams params = new WindowManager.LayoutParams();

    public MainPresenter(MainView mHomeView) {
        this.mMainView = mHomeView;
        this.mMainModel = new MainModel();
    }

    public void loadExistTable() {
        mMainView.loadExistTableName(mMainModel.getExistTableName());
    }

    public void setTableStatus() {
        mMainView.loadStatus(mMainModel.setExistTableStatus());
    }

    public void loadPath(Context context,Intent data) {
        mMainView.loadPath(mMainModel.loadPath(context,data));
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initWindowManager(WindowManager windowManager, View inflate) {

        // 悬浮窗
        // 靠手机屏幕的左边居中显示
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        // 如果设置以下属性，那么该悬浮窗口将不可触摸，不接受输入事件，不影响其他窗口事件的传递和分发
        // params.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL
        // |LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
        // 可以设定坐标
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 透明度
        // params.alpha=0.8f;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManager.addView(inflate, params);
        inflate.setOnTouchListener((v, event) -> {
            // 获取相对屏幕的坐标，即以屏幕左上角为原点
            x = event.getRawX();
            y = event.getRawY() - top; // 25是系统状态栏的高度
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 获取相对View的坐标，即以此View左上角为原点
                    mTouchStartX = event.getX();
                    mTouchStartY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    // 更新浮动窗口位置参数
                    params.x = (int) (x - mTouchStartX);
                    params.y = (int) (y - mTouchStartY);
                    windowManager.updateViewLayout(v, params);
                    break;
                case MotionEvent.ACTION_UP:
                    // 更新浮动窗口位置参数
                    params.x = (int) (x - mTouchStartX);
                    params.y = (int) (y - mTouchStartY);
                    windowManager.updateViewLayout(v, params);
                    // 可以在此记录最后一次的位置
                    mTouchStartX = mTouchStartY = 0;
                    break;
            }
            return true;
        });
        inflate.findViewById(R.id.image).setOnClickListener(v -> windowManager.removeViewImmediate(inflate));
    }
}

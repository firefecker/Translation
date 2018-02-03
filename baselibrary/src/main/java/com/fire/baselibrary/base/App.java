package com.fire.baselibrary.base;

import android.app.Application;
import com.fire.baselibrary.utils.ImageLoader;
import com.fire.baselibrary.utils.ToastUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 *
 * @author fire
 * @date 2017/12/28
 * Description:
 */

public class App extends Application {

    public static Application mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        mApp = this;
    }

    private void init() {
        ToastUtils.init(this);
        ImageLoader.getInstance(this);
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .tag("Translation")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return super.isLoggable(priority, tag);
            }
        });
    }

    public static Application getInstance() {
        return mApp;
    }
}

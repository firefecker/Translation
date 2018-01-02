package com.fire.translation;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;
import com.fire.baselibrary.network.RetrofitClient;
import com.fire.translation.entity.Test;
import com.fire.baselibrary.base.BaseActivity;
import com.fire.baselibrary.network.NetworkListener;
import com.fire.baselibrary.network.OkhttpClientImpl;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.SafeObserver;

/**
 * @author fire
 */
public class MainActivity extends BaseActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void initData() {
        OkhttpClientImpl.getInstance()
                .get()
                .setOnListener(new NetworkListener<Test>(Test.class) {
                    @Override
                    public void onSucess(Test string) {
                        String s = new Gson().toJson(string);
                        Logger.e(s);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Logger.e(e.getMessage());
                    }
                });

        //RetrofitClient.getInstance()
        //        .getServiceApi()
        //        .beforeNews("2017-12-26")
        //        .subscribe(new Fun);
    }
}

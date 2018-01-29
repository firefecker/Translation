package com.fire.translation.ui.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import com.fire.translation.ui.activity.LockScreenActivity;

/**
 *
 * @author fire
 * @date 2018/1/29
 * Description:
 */

@SuppressLint("Registered")
public class ScreenOffService extends Service {

    @Override public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mScreenOffReceiver,
                intentFilter);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        unregisterComponent();
    }

    @Override public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    private BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF) || intent.getAction()
                    .equals(Intent.ACTION_SCREEN_ON)) {
                Intent intent1 = new Intent(ScreenOffService.this, LockScreenActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        }
    };

    public void unregisterComponent() {
        // TODO Auto-generated method stub
        if (mScreenOffReceiver != null) {
            unregisterReceiver(mScreenOffReceiver);
        }
    }
}

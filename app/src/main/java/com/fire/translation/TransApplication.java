package com.fire.translation;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.fire.baselibrary.base.App;
import com.fire.baselibrary.utils.ListUtils;
import com.fire.translation.constant.Constant;
import com.fire.translation.db.CipherOpenHelper;
import com.fire.translation.db.DBConfig;
import com.fire.translation.db.DbModelSQLiteTypeMapping;
import com.fire.translation.db.TableInfo;
import com.fire.translation.db.entities.DbModel;
import com.fire.translation.utils.AssetsUtils;
import com.iflytek.cloud.SpeechUtility;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.impl.DefaultStorIOSQLite;
import com.tencent.bugly.crashreport.CrashReport;
import com.youdao.sdk.app.YouDaoApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by fire on 2018/1/11.
 * Date：2018/1/11
 * Author: fire
 * Description:
 */

public class TransApplication extends App {

    public static TransApplication mTransApp;
    private CipherOpenHelper mDbOpenHelper;
    private DBConfig mDbConfig;

    @Override
    public void onCreate() {
        initBugly();

        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));
        super.onCreate();
        YouDaoApplication.init(this, getString(R.string.yodaoappid));
        mTransApp = this;
        Constant.SORT = ListUtils.stringToString(this, R.array.sort, R.array.sort_value,
                PreferenceManager.getDefaultSharedPreferences(this).getString("sort_plan", "1"));
        unZipFile();
        initDBHelper();
    }

    private void initBugly() {

        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), "2816ae5865", false);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initDBHelper() {
        DBConfig build = DBConfig.builder()
                .dbDir(getDatabasePath(".").getAbsolutePath())
                .dbName(Constant.SQLONENAME)
                .version(1)
                .build();
        CipherOpenHelper cipherOpenHelper = new CipherOpenHelper(this,
                build.getDbDir(), build.getDbName(), build.getVersion());
        initSqlite(cipherOpenHelper);
        mDbConfig = DBConfig.builder()
                .dbDir(getDatabasePath(".").getAbsolutePath())
                .dbName(Constant.BASESQLNAME)
                .version(1)
                .build();
        mDbOpenHelper = new CipherOpenHelper(this, mDbConfig.getDbDir(), mDbConfig.getDbName(),
                mDbConfig.getVersion());
    }

    public void initDBHelper(String dbName) {
        DBConfig build = DBConfig.builder()
                .dbDir(getDatabasePath(".").getAbsolutePath())
                .dbName(dbName)
                .version(1)
                .build();
        CipherOpenHelper cipherOpenHelper = new CipherOpenHelper(this,
                build.getDbDir(), build.getDbName(), build.getVersion());
        initSqlite(cipherOpenHelper);
        mDbConfig = DBConfig.builder()
                .dbDir(getDatabasePath(".").getAbsolutePath())
                .dbName(Constant.BASESQLNAME)
                .version(1)
                .build();
        mDbOpenHelper = new CipherOpenHelper(this, mDbConfig.getDbDir(), mDbConfig.getDbName(),
                mDbConfig.getVersion());
    }

    public StorIOSQLite initSqlite(CipherOpenHelper mDbOpenHelper) {
        DefaultStorIOSQLite.CompleteBuilder completeBuilder = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(mDbOpenHelper)
                .addTypeMapping(DbModel.class, new DbModelSQLiteTypeMapping());
        return TableInfo.buildTypeMapping(completeBuilder);
    }

    public StorIOSQLite initSqlite() {
        DefaultStorIOSQLite.CompleteBuilder completeBuilder = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(mDbOpenHelper)
                .addTypeMapping(DbModel.class, new DbModelSQLiteTypeMapping());
        return TableInfo.buildTypeMapping(completeBuilder);
    }

    private void unZipFile() {
        try {
            File databasePath = getDatabasePath(Constant.BASESQLNAME);
            if (!databasePath.exists()) {
                AssetsUtils.unZip(this, Constant.BASEZIPNAME,
                        getDatabasePath(".").getAbsolutePath());
                AssetsUtils.unZip(this, Constant.ZIPONENAME,
                        getDatabasePath(".").getAbsolutePath());
            } else {
                File file = getDatabasePath(Constant.SQLONENAME);
                if (!file.exists()) {
                    AssetsUtils.unZip(this, Constant.ZIPONENAME,
                            getDatabasePath(".").getAbsolutePath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


}

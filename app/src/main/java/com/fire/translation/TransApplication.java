package com.fire.translation;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
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
import com.youdao.sdk.app.YouDaoApplication;
import java.io.File;
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
        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));
        super.onCreate();
        YouDaoApplication.init(this, getString(R.string.yodaoappid));
        mTransApp = this;
        Constant.SORT = ListUtils.stringToString(this, R.array.sort, R.array.sort_value,
                PreferenceManager.getDefaultSharedPreferences(this).getString("sort_plan", "1"));
        unZipFile();
        initDBHelper();
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

}

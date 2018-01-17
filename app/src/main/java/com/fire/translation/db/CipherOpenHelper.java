package com.fire.translation.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.fire.translation.constant.Constant;
import com.orhanobut.logger.Logger;

/**
 * Created by fire on 2018/1/12.
 * Date：2018/1/12
 * Author: fire
 * Description:
 */

public class CipherOpenHelper extends SQLiteOpenHelper {
    public interface DBOpenHelperEvent {
        void onDBCreate(SQLiteOpenHelper sqLiteOpenHelper, SQLiteDatabase sqLiteDatabase);

        void onDBOpen(SQLiteOpenHelper sqLiteOpenHelper, SQLiteDatabase db);

        void onDBUpgrade(SQLiteOpenHelper sqLiteOpenHelper, SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion);
    }

    private DBOpenHelperEvent dbOpenHelperEvent;

    public void registerDBOpenHelperEvent(DBOpenHelperEvent dbOpenHelperEvent) {
        this.dbOpenHelperEvent = dbOpenHelperEvent;
    }

    public CipherOpenHelper(final Context context, String dbDir, String dbName, int version) {
        super(new DatabaseContext(context, dbDir), dbName, null, version);
    }
    public CipherOpenHelper(final Context context, String dbName, int version) {
        super(context,dbName, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Logger.d("onCreate");
        if (getDatabaseName().equals(Constant.BASESQLNAME)) {
            sqLiteDatabase.execSQL(
                    "create table if not exists translaterecord (_id TEXT primary key, "
                    + "translations TEXT , "
                    + "explains TEXT , "
                    + "usphonetic TEXT, "
                    + "ukphonetic TEXT, "
                    + "cto TEXT, "
                    + "mquery TEXT, "
                    + "phonetic TEXT, "
                    + "le TEXT, "
                    + "mfrom TEXT, "
                    + "dictweburl TEXT, "
                    + "deeplink TEXT, "
                    + "dictdeeplink TEXT, "
                    + "errorcode INTEGER, "
                    + "start INTEGER)");
        }
        if (dbOpenHelperEvent != null) {
            dbOpenHelperEvent.onDBCreate(this, sqLiteDatabase);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Logger.d("onOpen");
        if (getDatabaseName().equals(Constant.BASESQLNAME)) {
            db.execSQL(
                    "create table if not exists translaterecord (_id varchar(256) primary key, "
                    + "translations text , "
                    + "explains text , "
                    + "usphonetic text, "
                    + "ukphonetic text, "
                    + "cto text, "
                    + "mquery text, "
                    + "phonetic text, "
                    + "le text, "
                    + "mfrom text, "
                    + "dictweburl text, "
                    + "deeplink text, "
                    + "dictdeeplink text, "
                    + "errorcode integer, "
                    + "start integer)");
        }
        if (dbOpenHelperEvent != null) {
            dbOpenHelperEvent.onDBOpen(this, db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Logger.d("onUpgrade");
        if (dbOpenHelperEvent != null) {
            dbOpenHelperEvent.onDBUpgrade(this, sqLiteDatabase, oldVersion, newVersion);
        }
    }
}

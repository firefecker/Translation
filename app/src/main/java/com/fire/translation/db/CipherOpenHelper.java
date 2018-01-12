package com.fire.translation.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
        if (dbOpenHelperEvent != null) {
            dbOpenHelperEvent.onDBCreate(this, sqLiteDatabase);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Logger.d("onOpen");
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

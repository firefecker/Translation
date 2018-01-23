package com.fire.translation.db;

import android.content.Context;
import android.database.Cursor;
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
        } else {
            if (exits("word",db) && !checkColumnExists2(db,"word","time")) {
                db.execSQL("alter table word add time text;");
            }
            if (exits("word",db) && !checkColumnExists2(db,"word","new_word")) {
                db.execSQL("alter table word add new_word integer;");
            }
        }
        if (dbOpenHelperEvent != null) {
            dbOpenHelperEvent.onDBOpen(this, db);
        }
    }

    /**
     * 检查表是否存在
     * @param table
     * @param db
     * @return
     */
    public boolean exits(String table,SQLiteDatabase db){
        boolean exits = false;
        Cursor cursor = db.rawQuery("select * from sqlite_master where name="+"'"+table+"'", null);
        if(cursor.getCount()!=0){
            exits = true;
        }
        return exits;
    }

    /**
     * 检查表中某列是否存在
     * @param db
     * @param tableName 表名
     * @param columnName 列名
     * @return
     */
    public static boolean checkColumnExists2(SQLiteDatabase db, String tableName
            , String columnName) {
        String queryStr = "select sql from sqlite_master where type = 'table' and name = '%s'";
        queryStr = String.format(queryStr, tableName);
        Cursor c = db.rawQuery(queryStr, null);
        String tableCreateSql = null;
        try {
            if (c != null && c.moveToFirst()) {
                tableCreateSql = c.getString(c.getColumnIndex("sql"));
            }
        } finally {
            if (c != null)
                c.close();
        }
        if (tableCreateSql != null && tableCreateSql.contains(columnName))
            return true;
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Logger.d("onUpgrade");
        if (dbOpenHelperEvent != null) {
            dbOpenHelperEvent.onDBUpgrade(this, sqLiteDatabase, oldVersion, newVersion);
        }
    }
}

package com.fire.translation.db;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import com.fire.translation.utils.FileUtils;
import java.io.File;

/**
 *
 * @author fire
 * @date 2018/1/12
 * Description:
 */

public class DatabaseContext extends ContextWrapper {

    private String dbDirPath;

    public DatabaseContext(Context base, String dbPath) {
        super(base);
        this.dbDirPath = dbPath;
    }

    @Override
    public File getDatabasePath(String name) {
        if (!FileUtils.isFolderExists(dbDirPath)) {
            FileUtils.makeDirectory(dbDirPath, true);
        }
        return new File(this.dbDirPath, name);
    }

    /**
     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
            SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(
                getDatabasePath(name), null);
        return result;
    }

    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @see android.content.ContextWrapper#openOrCreateDatabase(java.lang.String,
     * int, android.database.sqlite.SQLiteDatabase.CursorFactory,
     * android.database.DatabaseErrorHandler)
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
            SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(
                getDatabasePath(name), null);
        return result;
    }
}

package com.fire.translation.db;

import android.database.Cursor;
import android.support.annotation.NonNull;
import com.fire.translation.db.entities.DbModel;
import com.pushtorefresh.storio3.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResolver;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio3.sqlite.operations.get.DefaultGetResolver;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResolver;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;

/**
 * Created by fire on 2018/1/12.
 * Date：2018/1/12
 * Author: fire
 * Description:
 */

public class DbModelSQLiteTypeMapping extends SQLiteTypeMapping<DbModel> {

    public static class DBModelPutResolver extends PutResolver<DbModel> {

        @NonNull
        @Override
        public PutResult performPut(@NonNull StorIOSQLite storIOSQLite, @NonNull DbModel object) {
            return PutResult.newUpdateResult(0,"");
        }
    }

    public static class DBModelGetResolver extends DefaultGetResolver<DbModel> {

        @NonNull
        @Override
        public DbModel mapFromCursor(@NonNull StorIOSQLite storIOSQLite, @NonNull Cursor cursor) {
            DbModel result = new DbModel();
            int columnCount = cursor.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                result.add(cursor.getColumnName(i), cursor.getString(i));
            }
            return result;
        }
    }

    public static class DBModelDeleteResolver extends DeleteResolver<DbModel> {
        @NonNull
        @Override
        public DeleteResult performDelete(@NonNull StorIOSQLite storIOSQLite, @NonNull DbModel object) {
            return DeleteResult.newInstance(0,"");
        }
    }


    public DbModelSQLiteTypeMapping() {
        super(new DBModelPutResolver(),
                new DBModelGetResolver(),
                new DBModelDeleteResolver());
    }

}


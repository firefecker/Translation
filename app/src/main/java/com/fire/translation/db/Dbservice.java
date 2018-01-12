package com.fire.translation.db;

import com.fire.translation.TransApplication;
import com.fire.translation.constant.Constant;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.TableName;
import com.fire.translation.utils.DateUtils;
import com.pushtorefresh.storio3.Optional;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio3.sqlite.queries.Query;
import com.pushtorefresh.storio3.sqlite.queries.RawQuery;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.Date;
import java.util.List;

/**
 * Created by fire on 2018/1/12.
 * Date：2018/1/12
 * Author: fire
 * Description:
 */

public class Dbservice {

    private static final Dbservice ourInstance = new Dbservice();
    private StorIOSQLite mStorIOSQLite;

    public static Dbservice getInstance() {
        return ourInstance;
    }

    private Dbservice() {
        mStorIOSQLite = TransApplication.mTransApp.initSqlite();
    }

    public Dbservice defaultDbConfig() {
        mStorIOSQLite = TransApplication.mTransApp.initSqlite();
        return this;
    }

    public StorIOSQLite getStorIOSQLite() {
        return mStorIOSQLite;
    }

    public Dbservice setDbConfig(String dbDir, String dbName) {
        DBConfig build = DBConfig.builder().dbDir(dbDir).dbName(dbName).version(1).build();
        CipherOpenHelper cipherOpenHelper = new CipherOpenHelper(TransApplication.mTransApp,
                build.getDbDir(), build.getDbName(), build.getVersion());
        mStorIOSQLite = TransApplication.mTransApp.initSqlite(cipherOpenHelper);
        return this;
    }

    public Flowable<List<Record>> getAllRecord() {
        return mStorIOSQLite
                .get()
                .listOfObjects(Record.class)
                .withQuery(RawQuery.builder()
                        .query(String.format("select * from %s ", Record.__TABLE__))
                        .build())
                .prepare()
                .asRxFlowable(BackpressureStrategy.LATEST)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<TableName>> getAllTableName() {
        return mStorIOSQLite
                .get()
                .listOfObjects(TableName.class)
                .withQuery(Query.builder().table(TableName.__TABLE__).build())
                .prepare()
                .asRxFlowable(BackpressureStrategy.LATEST)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<Optional<TableName>> getExistTableName() {
        return mStorIOSQLite
                .get()
                .object(TableName.class)
                .withQuery(Query.builder()
                        .table(TableName.__TABLE__)
                        .where(String.format("%s = ?", TableName.C_FLAG_1))
                        .whereArgs(1)
                        .build())
                .prepare()
                .asRxFlowable(BackpressureStrategy.LATEST)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public PutResult updateTableStatus(TableName tableName) {
        return mStorIOSQLite
                .put()
                .object(tableName)
                .prepare()
                .executeAsBlocking();
    }

    public TableName getFirstTable() {
        return mStorIOSQLite
                .get()
                .object(TableName.class)
                .withQuery(Query.builder()
                        .table(TableName.__TABLE__)
                        .where(String.format("%s = ?", TableName.C_NAME))
                        .whereArgs("ciku_01")
                        .build())
                .prepare()
                .executeAsBlocking();
    }

    public PutResult insertRecord(Record record) {
        return mStorIOSQLite
                .put()
                .object(record)
                .prepare()
                .executeAsBlocking();
    }

    public Record getRecord() {
        return mStorIOSQLite
                .get()
                .object(Record.class)
                .withQuery(Query.builder()
                        .table(Record.__TABLE__)
                        .where(String.format("%s = ?", Record.C_RECORD_DATE))
                        .whereArgs(DateUtils.getFormatDate1(new Date(),DateUtils.dateFormat1))
                        .build())
                .prepare()
                .executeAsBlocking();
    }
}

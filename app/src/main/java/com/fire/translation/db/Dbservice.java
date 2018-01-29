package com.fire.translation.db;

import com.fire.translation.TransApplication;
import com.fire.translation.constant.Constant;
import com.fire.translation.db.entities.DailyEntity;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.TableName;
import com.fire.translation.db.entities.Tanslaterecord;
import com.fire.translation.db.entities.Word;
import com.fire.translation.utils.DateUtils;
import com.pushtorefresh.storio3.Optional;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResult;
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

    public Dbservice setDbConfig(String dbName) {
        DBConfig build = DBConfig.builder().dbDir(TransApplication.mTransApp.getDatabasePath(".").getAbsolutePath()).dbName(dbName).version(1).build();
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

    public Flowable<Optional<TableName>> getExistTableName(String summary) {
        return mStorIOSQLite
                .get()
                .object(TableName.class)
                .withQuery(Query.builder()
                        .table(TableName.__TABLE__)
                        .where(String.format("%s = ?", TableName.C_CIKU_NAME))
                        .whereArgs(summary)
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

    public Record getRecord(String data) {
        return mStorIOSQLite
                .get()
                .object(Record.class)
                .withQuery(Query.builder()
                        .table(Record.__TABLE__)
                        .where(String.format("%s = ?", Record.C_RECORD_DATE))
                        .whereArgs(data)
                        .build())
                .prepare()
                .executeAsBlocking();
    }

    public List<Word> getAllWords() {
        return mStorIOSQLite
                .get()
                .listOfObjects(Word.class)
                .withQuery(Query.builder()
                        .table(Word.__TABLE__)
                        .build())
                .prepare()
                .executeAsBlocking();
    }

    public Tanslaterecord getCurrentTranslateAsExecute(String translations,String to,String from,String query) {
        return mStorIOSQLite
                .get()
                .object(Tanslaterecord.class)
                .withQuery(Query.builder()
                        .table(Tanslaterecord.__TABLE__)
                        .where(String.format("%s = ? and %s = ? and %s = ? and %s = ?",
                                Tanslaterecord.C_TRANSLATIONS,Tanslaterecord.C_CTO,Tanslaterecord.C_MFROM,Tanslaterecord.C_MQUERY))
                        .whereArgs(translations,to,from,query)
                        .build())
                .prepare()
                .executeAsBlocking();
    }

    public Flowable<Optional<Tanslaterecord>> getCurrentTranslateAsRx(String translations,String to,String from,String query) {
        return mStorIOSQLite
                .get()
                .object(Tanslaterecord.class)
                .withQuery(Query.builder()
                        .table(Tanslaterecord.__TABLE__)
                        .where(String.format("%s = ? and %s = ? and %s = ? and %s = ?",
                                Tanslaterecord.C_TRANSLATIONS,Tanslaterecord.C_CTO,Tanslaterecord.C_MFROM,Tanslaterecord.C_MQUERY))
                        .whereArgs(translations,to,from,query)
                        .build())
                .prepare()
                .asRxFlowable(BackpressureStrategy.LATEST);
    }

    public PutResult insertTanslateRecord(Tanslaterecord currentTranslate) {
        return mStorIOSQLite
                .put()
                .object(currentTranslate)
                .prepare()
                .executeAsBlocking();
    }

    public Flowable<List<Tanslaterecord>> getAllTranslateRecord() {
        return mStorIOSQLite
                .get()
                .listOfObjects(Tanslaterecord.class)
                .withQuery(Query.builder()
                        .table(Tanslaterecord.__TABLE__)
                        .build())
                .prepare()
                .asRxFlowable(BackpressureStrategy.LATEST)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public List<Word> getCurrentWord(int reviews) {
        String format = "";
        switch (Constant.SORT) {
            case "正序":
                format = String.format("%s = ? ORDER BY %s ASC LIMIT '%d'", Word.C_REMEMBER, Word.C_ID, reviews);
                break;
            case "倒序":
                format = String.format("%s = ? ORDER BY %s DESC LIMIT '%d'", Word.C_REMEMBER, Word.C_ID, reviews);
                break;
            case "乱序":
                format = String.format("%s = ? ORDER BY RANDOM() LIMIT '%d'", Word.C_REMEMBER, reviews);
                break;
            default:
                format = String.format("%s = ? limit '%d'", Word.C_REMEMBER, reviews);
                break;
        }
        return mStorIOSQLite .get()
                .listOfObjects(Word.class)
                .withQuery(Query.builder()
                        .table(Word.__TABLE__)
                        .where(format)
                        .whereArgs("0")
                        .build())
                .prepare()
              .executeAsBlocking();
    }

    public Flowable<PutResult> UpDateWordStatus(Word word) {
        return mStorIOSQLite
                .put()
                .object(word)
                .prepare()
                .asRxFlowable(BackpressureStrategy.LATEST)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<PutResult> updateJsnum(Record record) {
        return mStorIOSQLite
                .put()
                .object(record)
                .prepare()
                .asRxFlowable(BackpressureStrategy.LATEST)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public PutResult updateRecord(Record record) {
        return mStorIOSQLite
                .put()
                .object(record)
                .prepare()
                .executeAsBlocking();
    }

    public List<Word> getCurrentRememberWord(int reviews) {
        return mStorIOSQLite .get()
                .listOfObjects(Word.class)
                .withQuery(Query.builder()
                        .table(Word.__TABLE__)
                        .where(String.format("%s = ? and %s = ? limit %d",Word.C_REMEMBER,Word.C_TIME,reviews))
                        .whereArgs("1",DateUtils.formatDateToString(new Date(),DateUtils.dateFormat1))
                        .build())
                .prepare()
                .executeAsBlocking();
    }

    public Flowable<List<Record>> getAllRecordData() {
        // WHERE %s NOTNULL
        String query = String.format("SELECT * FROM %s",Record.__TABLE__);
        return mStorIOSQLite.get()
                .listOfObjects(Record.class)
                .withQuery(RawQuery.builder().query(query).build())
                .prepare()
                .asRxFlowable(BackpressureStrategy.BUFFER)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<Word>> getAllWordData(int newWord,int remomber) {
        String query = "";
        if (newWord == 1) {
            if (remomber == 0) {
                query = String.format("SELECT * FROM %s where %s = '1'",Word.__TABLE__,Word.C_NEWWORD);
            } else {
                query = String.format("SELECT * FROM %s where %s = '1' or %s = '1'",Word.__TABLE__,Word.C_REMEMBER,Word.C_NEWWORD);
            }
        } else {
            if (remomber == 0) {
                query = String.format("SELECT * FROM %s where %s = '0' and %s = '0' limit 0",Word.__TABLE__,Word.C_REMEMBER,Word.C_NEWWORD);
            } else {
                query = String.format("SELECT * FROM %s where %s = '1'",Word.__TABLE__,Word.C_REMEMBER);
            }
        }
        return mStorIOSQLite.get()
                .listOfObjects(Word.class)
                .withQuery(RawQuery.builder().query(query).build())
                .prepare()
                .asRxFlowable(BackpressureStrategy.BUFFER)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<DeleteResult> deleteRecord(Record record) {
        return mStorIOSQLite
                .delete()
                .object(record)
                .prepare()
                .asRxFlowable(BackpressureStrategy.LATEST)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<DeleteResult> deleteTranslateRecord(Tanslaterecord tanslaterecord) {
        return mStorIOSQLite
                .delete()
                .object(tanslaterecord)
                .prepare()
                .asRxFlowable(BackpressureStrategy.LATEST)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public PutResult insertDaily(DailyEntity dailyEntity) {
        return mStorIOSQLite
                .put()
                .object(dailyEntity)
                .prepare()
                .executeAsBlocking();
    }

    public Flowable<Optional<DailyEntity>> getCurrentDailyEntity() {
        String query = String.format("SELECT * FROM %s where %s = '%s'",DailyEntity.__TABLE__,DailyEntity.C_DATELINE,DateUtils.getFormatDate1(new Date(), DateUtils.dateFormat1));
        return mStorIOSQLite
                .get()
                .object(DailyEntity.class)
                .withQuery(RawQuery.builder().query(query).build())
                .prepare()
                .asRxFlowable(BackpressureStrategy.LATEST)
                .observeOn(AndroidSchedulers.mainThread());
    }
}

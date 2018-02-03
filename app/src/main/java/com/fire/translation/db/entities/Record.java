package com.fire.translation.db.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;
import java.io.Serializable;

/**
 *
 * @author fire
 * @date 2018/1/12
 * Description:
 */

@StorIOSQLiteType(table = "record")
public class Record implements Serializable{

    public static final String __TABLE__ = "record";

    /**主键ID*/
    public static final String C_ID = "_id";
    @Nullable
    @StorIOSQLiteColumn(name = C_ID,key = true)
    int id;

    /**复习时间*/
    public static final String C_RECORD_DATE = "record_date";
    @Nullable
    @StorIOSQLiteColumn(name = C_RECORD_DATE)
    String recordDate  ;

    /**解锁次数*/
    public static final String C_RECORD_TIME = "record_time";
    @Nullable
    @StorIOSQLiteColumn(name = C_RECORD_TIME)
    int recordTime  ;

    /**复习词数*/
    public static final String C_REVIEW = "review";
    @StorIOSQLiteColumn(name = C_REVIEW)
    int review  ;

    /**坚持天数*/
    public static final String C_RECORD_DAYS = "record_days";
    @Nullable
    @StorIOSQLiteColumn(name = C_RECORD_DAYS)
    int recordDays  ;

    /**记录词数*/
    public static final String C_RECORD_COUNT = "record_count";
    @Nullable
    @StorIOSQLiteColumn(name = C_RECORD_COUNT)
    int recordCount;

    /**掌词数*/
    public static final String C_RECORD_WORDS = "record_words";
    @Nullable
    @StorIOSQLiteColumn(name = C_RECORD_WORDS)
    int recordWords;

    public Record() {
    }

    private Record(@Nullable int id, @Nullable String recordDate, @Nullable int recordTime, @Nullable int review, @Nullable int recordDays,
            @Nullable int recordCount, @Nullable int recordWords) {
        this.id = id;
        this.recordDate = recordDate;
        this.recordTime = recordTime;
        this.review = review;
        this.recordDays = recordDays;
        this.recordCount = recordCount;
        this.recordWords = recordWords;
    }

    @NonNull
    public static Record newRecord(@Nullable int id, @Nullable String recordDate, @Nullable int recordTime, @Nullable int review, @Nullable int recordDays,
            @Nullable int recordCount, @Nullable int recordWords) {
        return new Record(id, recordDate, recordTime, review, recordDays, recordCount, recordWords);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public int getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(int recordTime) {
        this.recordTime = recordTime;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getRecordDays() {
        return recordDays;
    }

    public void setRecordDays(int recordDays) {
        this.recordDays = recordDays;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getRecordWords() {
        return recordWords;
    }

    public void setRecordWords(int recordWords) {
        this.recordWords = recordWords;
    }

    @Override
    public String toString() {
        return "Record{" +
               "id=" + id +
               ", recordDate='" + recordDate + '\'' +
               ", recordTime=" + recordTime +
               ", review=" + review +
               ", recordDays=" + recordDays +
               ", recordCount=" + recordCount +
               ", recordWords=" + recordWords +
               '}';
    }
}

package com.fire.translation.db.entities;

import android.support.annotation.Nullable;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;
import java.io.Serializable;

/**
 * Created by fire on 2018/1/12.
 * Date：2018/1/12
 * Author: fire
 * Description:
 */

@StorIOSQLiteType(table = "table_name")
public class TableName implements Serializable {

    public static final String __TABLE__ = "table_name";

    /** 主键ID */
    public static final String C_ID = "_id";
    @Nullable
    @StorIOSQLiteColumn(name = C_ID, key = true)
    int id;

    /** 名称 */
    public static final String C_NAME = "name";
    @Nullable
    @StorIOSQLiteColumn(name = C_NAME)
    String name;

    /** 词库名称 */
    public static final String C_CIKU_NAME = "ciku_name";
    @Nullable
    @StorIOSQLiteColumn(name = C_CIKU_NAME)
    String cikuName;

    /** 单词个数 */
    public static final String C_WORD_COUNT = "word_count";
    @Nullable
    @StorIOSQLiteColumn(name = C_WORD_COUNT)
    int wordCount;

    /** 记住单词数 */
    public static final String C_REMEMBERED = "remembered";
    @Nullable
    @StorIOSQLiteColumn(name = C_REMEMBERED)
    int remembered;

    /** 音频的下载地址 */
    public static final String C_AUDIO_URL = "audio_url";
    @Nullable
    @StorIOSQLiteColumn(name = C_AUDIO_URL)
    String audioUrl;

    /** 词库下载地址 */
    public static final String C_URL = "url";
    @Nullable
    @StorIOSQLiteColumn(name = C_URL)
    String url;

    /** 音频大小 */
    public static final String C_AUDIO_SIZE = "audio_size";
    @Nullable
    @StorIOSQLiteColumn(name = C_AUDIO_SIZE)
    int audioSize;

    /** 词库大小 */
    public static final String C_CIKU_SIZE = "ciku_size";
    @Nullable
    @StorIOSQLiteColumn(name = C_CIKU_SIZE)
    int cikuSize;

    /***/
    public static final String C_EXTRS_1 = "extrs1";
    @Nullable
    @StorIOSQLiteColumn(name = C_EXTRS_1)
    String extrs1;

    /***/
    public static final String C_EXTRS_2 = "extrs2";
    @Nullable
    @StorIOSQLiteColumn(name = C_EXTRS_2)
    String extrs2;

    /** 标志1 */
    public static final String C_FLAG_1 = "flag1";
    @Nullable
    @StorIOSQLiteColumn(name = C_FLAG_1)
    int flag1;

    /** 标志2 */
    public static final String C_FLAG_2 = "flag2";
    @Nullable
    @StorIOSQLiteColumn(name = C_FLAG_2)
    int flag2;

    /** 类型 */
    public static final String C_TYPE = "type";
    @Nullable
    @StorIOSQLiteColumn(name = C_TYPE)
    String type;

    /** 类型ID */
    public static final String C_TYPE_ID = "type_id";
    @Nullable
    @StorIOSQLiteColumn(name = C_TYPE_ID)
    int typeId;

    /** 顺序 */
    public static final String C_SEQUENCE = "sequence";
    @Nullable
    @StorIOSQLiteColumn(name = C_SEQUENCE)
    int sequence;


    private TableName(@Nullable int id, @Nullable String name, @Nullable String cikuName,
            @Nullable int wordCount, @Nullable int remembered,
            @Nullable String audioUrl, @Nullable String url, @Nullable int audioSize,
            @Nullable int cikuSize, @Nullable String extrs1,
            @Nullable String extrs2, @Nullable int flag1, @Nullable int flag2,
            @Nullable String type, @Nullable int typeId, @Nullable int sequence) {
        this.id = id;
        this.name = name;
        this.cikuName = cikuName;
        this.wordCount = wordCount;
        this.remembered = remembered;
        this.audioUrl = audioUrl;
        this.url = url;
        this.audioSize = audioSize;
        this.cikuSize = cikuSize;
        this.extrs1 = extrs1;
        this.extrs2 = extrs2;
        this.flag1 = flag1;
        this.flag2 = flag2;
        this.type = type;
        this.typeId = typeId;
        this.sequence = sequence;
    }

    public static TableName newTableName(@Nullable int id, @Nullable String name,
            @Nullable String cikuName, @Nullable int wordCount, @Nullable int remembered,
            @Nullable String audioUrl, @Nullable String url, @Nullable int audioSize,
            @Nullable int cikuSize, @Nullable String extrs1,
            @Nullable String extrs2, @Nullable int flag1, @Nullable int flag2,
            @Nullable String type, @Nullable int typeId, @Nullable int sequence) {
        return new TableName(id, name, cikuName, wordCount, remembered, audioUrl, url, audioSize,
                cikuSize, extrs1, extrs2, flag1, flag2, type, typeId, sequence);
    }

    public TableName() {
    }

    @Nullable
    public int getId() {
        return id;
    }

    public void setId(@Nullable int id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getCikuName() {
        return cikuName;
    }

    public void setCikuName(@Nullable String cikuName) {
        this.cikuName = cikuName;
    }

    @Nullable
    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(@Nullable int wordCount) {
        this.wordCount = wordCount;
    }

    @Nullable
    public int getRemembered() {
        return remembered;
    }

    public void setRemembered(@Nullable int remembered) {
        this.remembered = remembered;
    }

    @Nullable
    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(@Nullable String audioUrl) {
        this.audioUrl = audioUrl;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    @Nullable
    public int getAudioSize() {
        return audioSize;
    }

    public void setAudioSize(@Nullable int audioSize) {
        this.audioSize = audioSize;
    }

    @Nullable
    public int getCikuSize() {
        return cikuSize;
    }

    public void setCikuSize(@Nullable int cikuSize) {
        this.cikuSize = cikuSize;
    }

    @Nullable
    public String getExtrs1() {
        return extrs1;
    }

    public void setExtrs1(@Nullable String extrs1) {
        this.extrs1 = extrs1;
    }

    @Nullable
    public String getExtrs2() {
        return extrs2;
    }

    public void setExtrs2(@Nullable String extrs2) {
        this.extrs2 = extrs2;
    }

    @Nullable
    public int getFlag1() {
        return flag1;
    }

    public void setFlag1(@Nullable int flag1) {
        this.flag1 = flag1;
    }

    @Nullable
    public int getFlag2() {
        return flag2;
    }

    public void setFlag2(@Nullable int flag2) {
        this.flag2 = flag2;
    }

    @Nullable
    public String getType() {
        return type;
    }

    public void setType(@Nullable String type) {
        this.type = type;
    }

    @Nullable
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(@Nullable int typeId) {
        this.typeId = typeId;
    }

    @Nullable
    public int getSequence() {
        return sequence;
    }

    public void setSequence(@Nullable int sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "TableName{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", cikuName='" + cikuName + '\'' +
               ", wordCount=" + wordCount +
               ", remembered=" + remembered +
               ", audioUrl='" + audioUrl + '\'' +
               ", url='" + url + '\'' +
               ", audioSize=" + audioSize +
               ", cikuSize=" + cikuSize +
               ", extrs1='" + extrs1 + '\'' +
               ", extrs2='" + extrs2 + '\'' +
               ", flag1=" + flag1 +
               ", flag2=" + flag2 +
               ", type='" + type + '\'' +
               ", typeId=" + typeId +
               ", sequence=" + sequence +
               '}';
    }
}

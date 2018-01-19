package com.fire.translation.db.entities;

import android.support.annotation.Nullable;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */
@StorIOSQLiteType(table = "word")
public class Word {

    public static final String __TABLE__ = "word";

    /** 主键ID */
    public static final String C_ID = "_id";
    @Nullable
    @StorIOSQLiteColumn(name = C_ID, key = true)
    int id;

    /** 单词 */
    public static final String C_WORD = "word";
    @Nullable
    @StorIOSQLiteColumn(name = C_WORD)
    String word;

    /** 词义 */
    public static final String C_INTERPRETATION = "interpretation";
    @Nullable
    @StorIOSQLiteColumn(name = C_INTERPRETATION)
    String interpretation;

    /** 发音 */
    public static final String C_PS = "ps";
    @Nullable
    @StorIOSQLiteColumn(name = C_PS)
    String ps;

    /** 词义 */
    public static final String C_RANDOM_ID = "random_id";
    @Nullable
    @StorIOSQLiteColumn(name = C_RANDOM_ID)
    int randomId;

    /** 词义 */
    public static final String C_REVERSE_ID = "reverse_id";
    @Nullable
    @StorIOSQLiteColumn(name = C_REVERSE_ID)
    int reverseId;

    /** 是否记住 */
    public static final String C_REMEMBER = "remember";
    @Nullable
    @StorIOSQLiteColumn(name = C_REMEMBER)
    int remember;

    /** 中文句子 */
    public static final String C_CN = "cn";
    @Nullable
    @StorIOSQLiteColumn(name = C_CN)
    String cn;

    /** 中文句子 */
    public static final String C_EN = "en";
    @Nullable
    @StorIOSQLiteColumn(name = C_EN)
    String en;

    /**
     * 时间
     */
    public static final String C_TIME = "time";
    @Nullable
    @StorIOSQLiteColumn(name = C_TIME)
    String time;

    /**
     * 是否为生词本
     */
    public static final String C_NEWWORD = "new_word";
    @Nullable
    @StorIOSQLiteColumn(name = C_NEWWORD)
    int newWord;

    private Word(@Nullable int id, @Nullable String word,
            @Nullable String interpretation, @Nullable String ps, @Nullable int randomId,
            @Nullable int reverseId, @Nullable int remember, @Nullable String cn,
            @Nullable String en,@Nullable String time,@Nullable int newWord) {
        this.id = id;
        this.word = word;
        this.interpretation = interpretation;
        this.ps = ps;
        this.randomId = randomId;
        this.reverseId = reverseId;
        this.remember = remember;
        this.cn = cn;
        this.en = en;
        this.time = time;
        this.newWord = newWord;
    }

    public static Word newWord(@Nullable int id, @Nullable String word,
            @Nullable String interpretation, @Nullable String ps, @Nullable int randomId,
            @Nullable int reverseId, @Nullable int remember, @Nullable String cn,
            @Nullable String en,@Nullable String time,@Nullable int newWord) {
        return new Word(id, word, interpretation, ps, randomId, reverseId, remember, cn, en,time,newWord);
    }

    public Word() {
    }

    @Nullable
    public int getId() {
        return id;
    }

    public void setId(@Nullable int id) {
        this.id = id;
    }

    @Nullable
    public String getWord() {
        return word;
    }

    public void setWord(@Nullable String word) {
        this.word = word;
    }

    @Nullable
    public String getInterpretation() {
        return interpretation;
    }

    public void setInterpretation(@Nullable String interpretation) {
        this.interpretation = interpretation;
    }

    @Nullable
    public String getPs() {
        return ps;
    }

    public void setPs(@Nullable String ps) {
        this.ps = ps;
    }

    @Nullable
    public int getRandomId() {
        return randomId;
    }

    public void setRandomId(@Nullable int randomId) {
        this.randomId = randomId;
    }

    @Nullable
    public int getReverseId() {
        return reverseId;
    }

    public void setReverseId(@Nullable int reverseId) {
        this.reverseId = reverseId;
    }

    @Nullable
    public int getRemember() {
        return remember;
    }

    public void setRemember(@Nullable int remember) {
        this.remember = remember;
    }

    @Nullable
    public String getCn() {
        return cn;
    }

    public void setCn(@Nullable String cn) {
        this.cn = cn;
    }

    @Nullable
    public String getEn() {
        return en;
    }

    public void setEn(@Nullable String en) {
        this.en = en;
    }

    @Nullable
    public String getTime() {
        return time;
    }

    public void setTime(@Nullable String time) {
        this.time = time;
    }

    @Nullable
    public int getNewWord() {
        return newWord;
    }

    public void setNewWord(@Nullable int newWord) {
        this.newWord = newWord;
    }

    @Override
    public String toString() {
        return "Word{" +
               "id=" + id +
               ", word='" + word + '\'' +
               ", interpretation='" + interpretation + '\'' +
               ", ps='" + ps + '\'' +
               ", randomId=" + randomId +
               ", reverseId=" + reverseId +
               ", remember=" + remember +
               ", cn='" + cn + '\'' +
               ", en='" + en + '\'' +
               ", time='" + time + '\'' +
               ", newWord='" + newWord + '\'' +
               '}';
    }
}

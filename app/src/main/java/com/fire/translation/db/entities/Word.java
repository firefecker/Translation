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

    /**主键ID*/
    public static final String C_ID = "_id";
    @Nullable
    @StorIOSQLiteColumn(name = C_ID,key = true)
    int id;

    /**单词*/
    public static final String C_WORD = "word";
    @Nullable
    @StorIOSQLiteColumn(name = C_WORD)
    String word;

    /**词义*/
    public static final String C_INTERPRETATION = "interpretation";
    @Nullable
    @StorIOSQLiteColumn(name = C_INTERPRETATION)
    String interpretation;

    /**发音*/
    public static final String C_PS = "ps";
    @Nullable
    @StorIOSQLiteColumn(name = C_PS)
    String ps;

    /**词义*/
    public static final String C_RANDOM_ID = "random_id";
    @Nullable
    @StorIOSQLiteColumn(name = C_RANDOM_ID)
    int randomId;

    /**词义*/
    public static final String C_REVERSE_ID = "reverse_id";
    @Nullable
    @StorIOSQLiteColumn(name = C_REVERSE_ID)
    int reverseId;

    /**是否记住*/
    public static final String C_REMEMBER = "remember";
    @Nullable
    @StorIOSQLiteColumn(name = C_REMEMBER)
    int remember;

    /**中文句子*/
    public static final String C_CN = "cn";
    @Nullable
    @StorIOSQLiteColumn(name = C_CN)
    String cn;

    /**中文句子*/
    public static final String C_EN = "en";
    @Nullable
    @StorIOSQLiteColumn(name = C_EN)
    String en;


}

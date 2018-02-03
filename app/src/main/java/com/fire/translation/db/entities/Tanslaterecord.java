package com.fire.translation.db.entities;

import android.support.annotation.Nullable;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;

/**
 *
 * @author fire
 * @date 2018/1/17
 * Description:
 */
@StorIOSQLiteType(table = "translaterecord")
public class Tanslaterecord {

    public static final String __TABLE__ = "translaterecord";

    /** 主键ID */
    public static final String C_ID = "_id";
    @Nullable
    @StorIOSQLiteColumn(name = C_ID, key = true)
    String id;

    public static final String C_TRANSLATIONS = "translations";
    @Nullable
    @StorIOSQLiteColumn(name = C_TRANSLATIONS)
    String translations;

    public static final String C_EXPLAINS = "explains";
    @Nullable
    @StorIOSQLiteColumn(name = C_EXPLAINS)
    String explains;

    public static final String C_USPHONETIC = "usphonetic";
    @Nullable
    @StorIOSQLiteColumn(name = C_USPHONETIC)
    String usphonetic;

    public static final String C_UKPHONETIC = "ukphonetic";
    @Nullable
    @StorIOSQLiteColumn(name = C_UKPHONETIC)
    String ukphonetic;

    public static final String C_CTO = "cto";
    @Nullable
    @StorIOSQLiteColumn(name = C_CTO)
    String cto;

    public static final String C_START = "start";
    @Nullable
    @StorIOSQLiteColumn(name = C_START)
    int start;

    public static final String C_ERRORCODE = "errorcode";
    @Nullable
    @StorIOSQLiteColumn(name = C_ERRORCODE)
    int errorcode;

    public static final String C_DICTDEEPLINK = "dictdeeplink";
    @Nullable
    @StorIOSQLiteColumn(name = C_DICTDEEPLINK)
    String dictdeeplink;

    public static final String C_DEEPLINK = "deeplink";
    @Nullable
    @StorIOSQLiteColumn(name = C_DEEPLINK)
    String deeplink;

    public static final String C_DICTWEBURL = "dictweburl";
    @Nullable
    @StorIOSQLiteColumn(name = C_DICTWEBURL)
    String dictweburl;

    public static final String C_MFROM = "mfrom";
    @Nullable
    @StorIOSQLiteColumn(name = C_MFROM)
    String mfrom;

    public static final String C_LE = "le";
    @Nullable
    @StorIOSQLiteColumn(name = C_LE)
    String le;

    public static final String C_PHONETIC = "phonetic";
    @Nullable
    @StorIOSQLiteColumn(name = C_PHONETIC)
    String phonetic;

    public static final String C_MQUERY = "mquery";
    @Nullable
    @StorIOSQLiteColumn(name = C_MQUERY)
    String mquery;

    public Tanslaterecord() {
    }

    private Tanslaterecord(@Nullable String id, @Nullable String translations, @Nullable String explains,
            @Nullable String usphonetic, @Nullable String ukphonetic, @Nullable String cto, @Nullable int start,
            @Nullable int errorcode, @Nullable String dictdeeplink, @Nullable String deeplink, @Nullable String dictweburl,
            @Nullable String mfrom, @Nullable String le, @Nullable String phonetic, @Nullable String mquery) {
        this.id = id;
        this.translations = translations;
        this.explains = explains;
        this.usphonetic = usphonetic;
        this.ukphonetic = ukphonetic;
        this.cto = cto;
        this.start = start;
        this.errorcode = errorcode;
        this.dictdeeplink = dictdeeplink;
        this.deeplink = deeplink;
        this.dictweburl = dictweburl;
        this.mfrom = mfrom;
        this.le = le;
        this.phonetic = phonetic;
        this.mquery = mquery;
    }

    public static Tanslaterecord newTanslaterecord(@Nullable String id, @Nullable String translations, @Nullable String explains,
            @Nullable String usphonetic, @Nullable String ukphonetic, @Nullable String cto, @Nullable int start,
            @Nullable int errorcode, @Nullable String dictdeeplink, @Nullable String deeplink, @Nullable String dictweburl,
            @Nullable String mfrom, @Nullable String le, @Nullable String phonetic, @Nullable String mquery) {
        return new Tanslaterecord(id,translations,explains,usphonetic,ukphonetic,cto,start,errorcode,dictdeeplink,deeplink,dictweburl,mfrom,le,phonetic,mquery);
    }

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@Nullable String id) {
        this.id = id;
    }

    @Nullable
    public String getTranslations() {
        return translations;
    }

    public void setTranslations(@Nullable String translations) {
        this.translations = translations;
    }

    @Nullable
    public String getExplains() {
        return explains;
    }

    public void setExplains(@Nullable String explains) {
        this.explains = explains;
    }

    @Nullable
    public String getUsphonetic() {
        return usphonetic;
    }

    public void setUsphonetic(@Nullable String usphonetic) {
        this.usphonetic = usphonetic;
    }

    @Nullable
    public String getUkphonetic() {
        return ukphonetic;
    }

    public void setUkphonetic(@Nullable String ukphonetic) {
        this.ukphonetic = ukphonetic;
    }

    @Nullable
    public String getCto() {
        return cto;
    }

    public void setCto(@Nullable String cto) {
        this.cto = cto;
    }

    @Nullable
    public int getStart() {
        return start;
    }

    public void setStart(@Nullable int start) {
        this.start = start;
    }

    @Nullable
    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(@Nullable int errorcode) {
        this.errorcode = errorcode;
    }

    @Nullable
    public String getDictdeeplink() {
        return dictdeeplink;
    }

    public void setDictdeeplink(@Nullable String dictdeeplink) {
        this.dictdeeplink = dictdeeplink;
    }

    @Nullable
    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(@Nullable String deeplink) {
        this.deeplink = deeplink;
    }

    @Nullable
    public String getDictweburl() {
        return dictweburl;
    }

    public void setDictweburl(@Nullable String dictweburl) {
        this.dictweburl = dictweburl;
    }

    @Nullable
    public String getMfrom() {
        return mfrom;
    }

    public void setMfrom(@Nullable String mfrom) {
        this.mfrom = mfrom;
    }

    @Nullable
    public String getLe() {
        return le;
    }

    public void setLe(@Nullable String le) {
        this.le = le;
    }

    @Nullable
    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(@Nullable String phonetic) {
        this.phonetic = phonetic;
    }

    @Nullable
    public String getMquery() {
        return mquery;
    }

    public void setMquery(@Nullable String mquery) {
        this.mquery = mquery;
    }

    @Override
    public String toString() {
        return "Tanslaterecord{" +
               "id='" + id + '\'' +
               ", translations='" + translations + '\'' +
               ", explains='" + explains + '\'' +
               ", usphonetic='" + usphonetic + '\'' +
               ", ukphonetic='" + ukphonetic + '\'' +
               ", cto='" + cto + '\'' +
               ", start=" + start +
               ", errorcode=" + errorcode +
               ", dictdeeplink='" + dictdeeplink + '\'' +
               ", deeplink='" + deeplink + '\'' +
               ", dictweburl='" + dictweburl + '\'' +
               ", mfrom='" + mfrom + '\'' +
               ", le='" + le + '\'' +
               ", phonetic='" + phonetic + '\'' +
               ", mquery='" + mquery + '\'' +
               '}';
    }
}

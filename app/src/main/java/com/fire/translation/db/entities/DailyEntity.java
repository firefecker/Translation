package com.fire.translation.db.entities;

import android.support.annotation.Nullable;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;

/**
 *
 * @author fire
 * @date 2017/12/28
 * Description:
 */
@StorIOSQLiteType(table = "dialy")
public class DailyEntity {

    public static final String __TABLE__ = "dialy";

    public static final String C_ID = "_id";
    @Nullable
    @StorIOSQLiteColumn(name = C_ID,key = true)
    String sid;

    public static final String C_TTS = "tts";
    @Nullable
    @StorIOSQLiteColumn(name = C_TTS)
    String tts;

    public static final String C_CONTENT = "content";
    @Nullable
    @StorIOSQLiteColumn(name = C_CONTENT)
    String content;

    public static final String C_NOTE = "note";
    @Nullable
    @StorIOSQLiteColumn(name = C_NOTE)
    String note;

    public static final String C_LOVE = "love";
    @Nullable
    @StorIOSQLiteColumn(name = C_LOVE)
    String love;

    public static final String C_TRANSLATION = "translation";
    @Nullable
    @StorIOSQLiteColumn(name = C_TRANSLATION)
    String translation;

    public static final String C_PICTURE = "picture";
    @Nullable
    @StorIOSQLiteColumn(name = C_PICTURE)
    String picture;

    public static final String C_PICTURE2 = "picture2";
    @Nullable
    @StorIOSQLiteColumn(name = C_PICTURE2)
    String picture2;

    public static final String C_CAPTION = "caption";
    @Nullable
    @StorIOSQLiteColumn(name = C_CAPTION)
    String caption;

    public static final String C_DATELINE = "dateline";
    @Nullable
    @StorIOSQLiteColumn(name = C_DATELINE)
    String dateline;

    public static final String C_SPV = "s_pv";
    @Nullable
    @StorIOSQLiteColumn(name = C_SPV)
    String s_pv;

    public static final String C_SPPV = "sp_pv";
    @Nullable
    @StorIOSQLiteColumn(name = C_SPPV)
    String sp_pv;

    public static final String C_FENXIANGIMG = "fenxiang_img";
    @Nullable
    @StorIOSQLiteColumn(name = C_FENXIANGIMG)
    String fenxiang_img;

    public static final String C_TAGS = "tags";
    @Nullable
    @StorIOSQLiteColumn(name = C_TAGS)
    String tag;


    public DailyEntity(String sid, String tts, String content, String note, String love,
            String translation, String picture, String picture2, String caption,
            String dateline, String s_pv, String sp_pv, String fenxiang_img, String tag) {
        this.sid = sid;
        this.tts = tts;
        this.content = content;
        this.note = note;
        this.love = love;
        this.translation = translation;
        this.picture = picture;
        this.picture2 = picture2;
        this.caption = caption;
        this.dateline = dateline;
        this.s_pv = s_pv;
        this.sp_pv = sp_pv;
        this.fenxiang_img = fenxiang_img;
        this.tag = tag;
    }

    public DailyEntity() {
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTts() {
        return tts;
    }

    public void setTts(String tts) {
        this.tts = tts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getS_pv() {
        return s_pv;
    }

    public void setS_pv(String s_pv) {
        this.s_pv = s_pv;
    }

    public String getSp_pv() {
        return sp_pv;
    }

    public void setSp_pv(String sp_pv) {
        this.sp_pv = sp_pv;
    }

    public String getFenxiang_img() {
        return fenxiang_img;
    }

    public void setFenxiang_img(String fenxiang_img) {
        this.fenxiang_img = fenxiang_img;
    }

    @Nullable
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    //public static class TagsBean {
    //    /**
    //     * id : null
    //     * name : null
    //     */
    //
    //    private String id;
    //    private String name;
    //
    //    public String getId() {
    //        return id;
    //    }
    //
    //    public void setId(String id) {
    //        this.id = id;
    //    }
    //
    //    public String getName() {
    //        return name;
    //    }
    //
    //    public void setName(String name) {
    //        this.name = name;
    //    }
    //
    //    @Override
    //    public String toString() {
    //        return "TagsBean{" +
    //               "id='" + id + '\'' +
    //               ", name='" + name + '\'' +
    //               '}';
    //    }
    //}

    @Override
    public String toString() {
        return "DailyEntity{" +
               "sid='" + sid + '\'' +
               ", tts='" + tts + '\'' +
               ", content='" + content + '\'' +
               ", note='" + note + '\'' +
               ", love='" + love + '\'' +
               ", translation='" + translation + '\'' +
               ", picture='" + picture + '\'' +
               ", picture2='" + picture2 + '\'' +
               ", caption='" + caption + '\'' +
               ", dateline='" + dateline + '\'' +
               ", s_pv='" + s_pv + '\'' +
               ", sp_pv='" + sp_pv + '\'' +
               ", fenxiang_img='" + fenxiang_img + '\'' +
               '}';
    }
}

package com.fire.translation.constant;

import android.Manifest;
import android.os.Environment;

/**
 * Created by fire on 2018/1/11.
 * Date：2018/1/11
 * Author: fire
 * Description:
 */

public class Constant {

    public static final String BASESQLNAME = "Beidanci.db";
    public static final String BASEZIPNAME = "Beidanci.zip";
    public static final String DOWNLOADBASE_URL = "http://api.secretlisa.com/dcsp/file/";

    ///data/data/com.fire.translation/databases/ciku_01.db
    public static String SQLONENAME = "ciku_01.db";
    public static String SQLTYPE = "初中词汇";
    public static final String ZIPONENAME = "ciku_01.zip";
    /**
     * 手机存储目录
     */
    public static final String PHONE_DIRECTORY = Environment.getDataDirectory().getAbsolutePath();
    public static final String SDCARD_DIRECTORY = Environment.getExternalStorageDirectory()
            .getAbsolutePath();

    /**
     * 启动照相请求
     */
    public static final int ACTION_IMAGE = 0x1;
    /**
     * 启动相册请求
     */
    public static final int ACTION_ALBUM = 0x2;

    public static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.RECORD_AUDIO
    };
    public static String SORT = "正序";
    public static String FILEPATH = "";
}

package com.fire.translation.utils;

import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fire on 2018/1/12.
 * Date：2018/1/12
 * Author: fire
 * Description:
 */

public class FileUtils {
    /**
     *
     * @param filePath
     * @return
     */
    public static boolean isFolderExists(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.isDirectory() && file.exists();
    }


    /**
     * 创建文件夹
     *
     * @throws IOException
     */
    public static boolean makeDirectory(String directory) {
        return makeDirectory(directory, true);
    }

    /**
     * 创建文件夹
     *
     * @throws IOException
     */
    public static boolean makeDirectory(String directory, boolean createParents) {
        boolean created;
        try {
            File dir = new File(directory);
            if (createParents) {
                created = dir.mkdirs();
            } else {
                if (dir.isFile()) {
                    created = dir.createNewFile();
                } else {
                    created = dir.mkdir();
                }
            }
        } catch (Exception e) {
            created = false;
        }
        return created;
    }

    /**
     * 生成文件路径和文件名
     *
     * @return
     */
    public static String getFileName() {
        String saveDir = Environment.getExternalStorageDirectory() + "/translation";
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir(); // 创建文件夹
        }
        // 用日期作为文件名，确保唯一性
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String fileName = saveDir + "/" + formatter.format(date) + ".png";

        return fileName;
    }

}

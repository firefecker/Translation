package com.fire.translation.utils;

import android.text.TextUtils;
import java.io.File;
import java.io.IOException;

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

}

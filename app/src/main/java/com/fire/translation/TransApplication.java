package com.fire.translation;

import com.fire.baselibrary.base.App;
import com.fire.translation.constant.Constant;
import com.fire.translation.utils.AssetsUtils;
import java.io.File;
import java.io.IOException;

/**
 * Created by fire on 2018/1/11.
 * Date：2018/1/11
 * Author: fire
 * Description:
 */

public class TransApplication extends App {
    @Override
    public void onCreate() {
        super.onCreate();
        unZipFile();
    }

    private void unZipFile() {
        try {
            File databasePath = getDatabasePath(Constant.BASESQLNAME);
            if (!databasePath.exists()) {
                AssetsUtils.unZip(this, Constant.BASEZIPNAME,getDatabasePath(".").getAbsolutePath());
                AssetsUtils.unZip(this,Constant.ZIPONENAME,getDatabasePath(".").getAbsolutePath());
            } else {
                File file = getDatabasePath(Constant.SQLONENAME);
                if (!file.exists()) {
                    AssetsUtils.unZip(this,Constant.ZIPONENAME,getDatabasePath(".").getAbsolutePath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

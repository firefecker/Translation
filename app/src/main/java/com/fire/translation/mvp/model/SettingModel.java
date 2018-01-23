package com.fire.translation.mvp.model;

import android.app.Application;
import com.fire.baselibrary.base.inter.IBaseModel;
import com.fire.translation.TransApplication;
import com.fire.translation.db.Dbservice;
import com.fire.translation.db.entities.TableName;
import com.fire.translation.mvp.view.SettingView;
import com.fire.translation.utils.AssetsUtils;
import com.fire.translation.utils.FileUtils;
import com.pushtorefresh.storio3.Optional;
import io.reactivex.Flowable;
import java.io.File;
import java.io.IOException;

/**
 * Created by fire on 2018/1/23.
 * Date：2018/1/23
 * Author: fire
 * Description:
 */

public class SettingModel implements IBaseModel {
    public Flowable<Optional<TableName>> getTableName(String summary) {
        return Dbservice.getInstance()
                .defaultDbConfig()
                .getExistTableName(summary);
    }

    public void setData(TableName tableName, Application mTransApp, SettingView settingView)
            throws IOException {
        File databasePath = mTransApp.getDatabasePath(
                String.format("%s.db", tableName.getName()));
        settingView.showLoadingView();
        if (!databasePath.exists()) {
            File file = new File(mTransApp.getFilesDir()
                                 + File.separator
                                 + String.format("%s.zip", tableName.getName()));
            if (file.exists()) {
                //解压到数据库文件夹
                FileUtils.unZip(file, mTransApp.getDatabasePath(".").getAbsolutePath());
                //切换数据库
                settingView.updateDataBase(tableName.getCikuName(),String.format("%s.db",tableName.getName()));
            } else {
                //下载
                settingView.downloadData(file.getName(),tableName.getName());
                //解压
                //切换数据库
            }
        } else {
            //切换数据库
            settingView.updateDataBase(tableName.getCikuName(),String.format("%s.db",tableName.getName()));
        }
    }
}

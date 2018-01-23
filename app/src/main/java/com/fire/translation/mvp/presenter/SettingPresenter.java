package com.fire.translation.mvp.presenter;

import android.app.Application;
import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.translation.TransApplication;
import com.fire.translation.db.entities.TableName;
import com.fire.translation.mvp.model.SettingModel;
import com.fire.translation.mvp.view.SettingView;
import java.io.IOException;

/**
 * Created by fire on 2018/1/23.
 * Date：2018/1/23
 * Author: fire
 * Description:
 */

public class SettingPresenter implements IBasePresenter {

    private SettingModel mSettingModel;
    private SettingView mSettingView;

    public SettingPresenter(SettingView settingView) {
        mSettingView = settingView;
        mSettingModel = new SettingModel();
    }

    public void getTableName(String summary) {
        mSettingView.getTableName(mSettingModel.getTableName(summary));
    }

    public void setData(TableName tableName, Application mTransApp) throws IOException {
        mSettingModel.setData(tableName,mTransApp,mSettingView);
    }

    public void updateDataBase(String mTableName,String mName) {
        mSettingView.updateDataBase(mTableName,mName);
    }
}

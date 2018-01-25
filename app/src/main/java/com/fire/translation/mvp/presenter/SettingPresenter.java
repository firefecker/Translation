package com.fire.translation.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.translation.R;
import com.fire.translation.TransApplication;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.TableName;
import com.fire.translation.mvp.model.SettingModel;
import com.fire.translation.mvp.view.SettingView;
import io.reactivex.Observable;
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

    public void downLoadData(Context context,String mName,String mTableName) {
        mSettingView.downLoadDataResult(mSettingModel.downLoadData(context,mName),mName,mTableName);
    }

    public void deleteRecord( Record record) {
        mSettingView.deleteRecord(mSettingModel.deleteRecord(record));
    }
    public void getRecord() {
        mSettingView.getRecord(mSettingModel.getRecord());
    }

    public void getCurrentRecord(String value) {
        mSettingView.getCurrentRecord(mSettingModel.getRecord(),value);
    }

    public void updateRecord(Record record1, Context context,String value) {
        mSettingView.updateRecord(mSettingModel.updateRecord(record1,context),value);
    }

    public void clearCache(Context context) {
        mSettingView.clearChache(mSettingModel.clearCache(context));
    }
}

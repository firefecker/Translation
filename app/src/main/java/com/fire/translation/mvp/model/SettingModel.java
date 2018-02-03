package com.fire.translation.mvp.model;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import com.fire.baselibrary.base.inter.IBaseModel;
import com.fire.baselibrary.utils.ListUtils;
import com.fire.translation.R;
import com.fire.translation.constant.Constant;
import com.fire.translation.db.Dbservice;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.TableName;
import com.fire.translation.mvp.view.SettingView;
import com.fire.translation.network.RetrofitClient;
import com.fire.translation.rx.DefaultObservable;
import com.fire.translation.utils.CacheUtils;
import com.fire.translation.utils.DateUtils;
import com.fire.translation.utils.FileUtils;
import com.pushtorefresh.storio3.Optional;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author fire
 * @date 2018/1/23
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
                settingView.updateDataBase(tableName.getCikuName(),
                        String.format("%s.db", tableName.getName()));
            } else {
                //下载
                settingView.downloadData(file.getName(), tableName.getCikuName());
                //解压
                //切换数据库
            }
        } else {
            //切换数据库
            settingView.updateDataBase(tableName.getCikuName(),
                    String.format("%s.db", tableName.getName()));
        }
    }

    public Observable<Boolean> downLoadData(Context context, String mName) {
        String name = mName;
        return RetrofitClient.getInstance()
                .setUrl(Constant.DOWNLOADBASE_URL)
                .getServiceApi()
                .downloadZip(name)
                .subscribeOn(Schedulers.io())
                .map(responseBody -> FileUtils.writeResponseBodyToDisk(context, responseBody,
                        name));
    }

    public Flowable<DeleteResult> deleteRecord(Record record) {
        return Dbservice.getInstance()
                .defaultDbConfig()
                .deleteRecord(record);
    }

    public Observable<Record> getRecord() {
        return DefaultObservable.create("")
                .map(s -> {
                    Record record = Dbservice.getInstance()
                            .defaultDbConfig()
                            .getRecord(DateUtils.getFormatDate1(new Date(), DateUtils.dateFormat1));
                    if (record == null) {
                        record = new Record();
                    }
                    return record;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<PutResult> updateRecord(Record record1, Context context) {
        String review = ListUtils.stringToString(context, R.array.newword,
                R.array.newword_value, PreferenceManager.getDefaultSharedPreferences(context)
                        .getString("word_plan", "2"));
        record1.setReview(Integer.parseInt(review));
        return Dbservice.getInstance()
                .defaultDbConfig()
                .updateJsnum(record1);
    }

    public Observable<Object> clearCache(Context context) {
        Context mContext = context;
        return Observable.create(e -> {
            CacheUtils.clearAllCache(mContext);
            e.onNext(new Object());
            e.onComplete();
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    }
}

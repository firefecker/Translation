package com.fire.translation.mvp.view;

import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.TableName;
import com.pushtorefresh.storio3.Optional;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResult;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by fire on 2018/1/23.
 * Date：2018/1/23
 * Author: fire
 * Description:
 */

public interface SettingView extends IBaseView {
    void getTableName(Flowable<Optional<TableName>> tableName);

    void downloadData(String name,String tableName);

    void updateDataBase(String mTableName,String mName);

    void downLoadDataResult(Observable<Boolean> booleanObservable,String name,String mTableName);

    void deleteRecord(Flowable<DeleteResult> deleteResultFlowable);

    void getRecord(Observable<Record> record);
}
package com.fire.translation.mvp.view;

import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.TableName;
import com.pushtorefresh.storio3.Optional;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 *
 * @author fire
 * @date 2018/1/23
 * Description:
 */

public interface SettingView extends IBaseView {
    void getTableName(Flowable<Optional<TableName>> tableName);

    void downloadData(String name,String tableName);

    void updateDataBase(String mTableName,String mName);

    void downLoadDataResult(Observable<Boolean> booleanObservable,String name,String mTableName);

    void deleteRecord(Flowable<DeleteResult> deleteResultFlowable);

    void getRecord(Observable<Record> record);

    void getCurrentRecord(Observable<Record> record,String value);

    void updateRecord(Flowable<PutResult> putResultFlowable,String value);

    void clearChache(Observable<Object> objectObservable);
}

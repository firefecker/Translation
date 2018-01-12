package com.fire.translation.mvp.view;

import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.translation.db.entities.TableName;
import com.pushtorefresh.storio3.Optional;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by fire on 2018/1/12.
 * Date：2018/1/12
 * Author: fire
 * Description:
 */

public interface MainView extends IBaseView{
    void loadExistTableName(Flowable<Optional<TableName>> existTableName);

    void loadStatus(Observable<PutResult> putResultObservable);
}

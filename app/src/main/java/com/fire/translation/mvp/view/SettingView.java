package com.fire.translation.mvp.view;

import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.translation.db.entities.TableName;
import com.pushtorefresh.storio3.Optional;
import io.reactivex.Flowable;

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
}

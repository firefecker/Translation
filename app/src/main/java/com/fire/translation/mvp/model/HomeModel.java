package com.fire.translation.mvp.model;

import com.fire.baselibrary.base.inter.IBaseModel;
import com.fire.translation.db.Dbservice;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.TableName;
import com.fire.translation.entity.DailyEntity;
import com.fire.translation.network.RetrofitClient;
import com.pushtorefresh.storio3.sqlite.Changes;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author fire
 * @date 2018/1/3
 * Description:
 */

public class HomeModel implements IBaseModel {

    public Observable<DailyEntity> getDsapi(String data) {
        return RetrofitClient.getInstance()
                .getServiceApi()
                .beforeNews(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<Changes> loadRecord() {
        Set<String> tables = new HashSet<>();
        tables.add(TableName.__TABLE__);
        tables.add(Record.__TABLE__);
        return Dbservice.getInstance()
                .defaultDbConfig()
                .getStorIOSQLite()
                .observeChangesInTables(tables, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .startWith(Changes.newInstance(""));
    }

    public Record getRecord() {
        return Dbservice.getInstance()
                .defaultDbConfig()
                .getRecord();
    }
}

package com.fire.translation.mvp.model;

import com.fire.baselibrary.base.inter.IBaseModel;
import com.fire.translation.db.Dbservice;
import com.fire.translation.db.entities.DailyEntity;
import com.fire.translation.network.RetrofitClient;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fire on 2018/1/29.
 * Date：2018/1/29
 * Author: fire
 * Description:
 */

public class SplashModel implements IBaseModel {

    public Observable<PutResult> getDsapi(String data) {
        return RetrofitClient.getInstance()
                .getServiceApi()
                .beforeNews(data)
                .subscribeOn(Schedulers.io())
                .map(dailyEntity -> Dbservice.getInstance()
                        .insertDaily(dailyEntity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

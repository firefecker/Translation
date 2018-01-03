package com.fire.translation.mvp.model;

import com.fire.baselibrary.base.inter.IBaseModel;
import com.fire.translation.entity.DailyEntity;
import com.fire.translation.network.RetrofitClient;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @author fire
 * @date 2018/1/3
 * Description:
 */

public class HomeModel implements IBaseModel {
    //http://jiankangshitang.batcatstudio.com/api/datacopy/getNotifyList?type=天然气开关
    /* @GET("datacopy/getNotifyList")
    Observable<Response<NotifyListResponse>> getnotifyList(@Query("type") String type);*/
    //http://open.iciba.com/dsapi/?date=
    public Observable<DailyEntity> getDsapi(String data) {
        return RetrofitClient.getInstance()
                .getServiceApi()
                .beforeNews(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

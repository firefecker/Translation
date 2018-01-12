package com.fire.translation.network;

import com.fire.translation.entity.DailyEntity;
import com.orhanobut.logger.Logger;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.ConnectionPool;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * @author fire
 * @date 2018/1/2
 * Description:
 */

public class RetrofitClient {

    private static RetrofitClient mRetrofitClient;
    private CookieManager mCookieManager;
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private Api mServiceApi;

    public static RetrofitClient getInstance() {
        if (mRetrofitClient == null) {
            synchronized (RetrofitClient.class) {
                if (mRetrofitClient == null) {
                    mRetrofitClient = new RetrofitClient();
                }
            }
        }
        return mRetrofitClient;
    }

    private RetrofitClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                message -> {
                    /*Logger.e(message)*/
                });
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        mCookieManager = new CookieManager();
        mCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        mOkHttpClient = new OkHttpClient.Builder()
                //拦截器
                .addInterceptor(logging)
                //网络拦截器
                .addNetworkInterceptor(logging)
                //连接失败后重新链接
                .retryOnConnectionFailure(true)
                //连接池
                .connectionPool(new ConnectionPool())
                //读取时间
                .readTimeout(60,TimeUnit.SECONDS)
                //写入时间
                .writeTimeout(60,TimeUnit.SECONDS)
                //链接时间
                .connectTimeout(20, TimeUnit.SECONDS)
                //cookie管理器
                .cookieJar(new JavaNetCookieJar(mCookieManager))
                //build
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl( "http://open.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();

        mServiceApi = mRetrofit.create(Api.class);
    }

    public RetrofitClient setmServiceApi(Api serviceApi) {
        mServiceApi = mRetrofit.create(Api.class);
        return this;
    }

    public Api getServiceApi() {
        return mServiceApi;
    }



    public interface Api {

        @GET("dsapi/")
        Observable<DailyEntity> beforeNews(@Query("date") String date);
    }

}

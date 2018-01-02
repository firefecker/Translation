package com.fire.baselibrary.network;

import com.orhanobut.logger.Logger;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
    private final ServiceApi mServiceApi;

    public static RetrofitClient getInstance() {
        if (mRetrofitClient == null) {
            synchronized (OkhttpClientImpl.class) {
                if (mRetrofitClient == null) {
                    mRetrofitClient = new RetrofitClient();
                }
            }
        }
        return mRetrofitClient;
    }

    private RetrofitClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Logger.i(message);
                    }
                });
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        mCookieManager = new CookieManager();
        mCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(20, TimeUnit.SECONDS)
                .cookieJar(new JavaNetCookieJar(mCookieManager))
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl( "http://open.iciba.com/dsapi/?")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();

        mServiceApi = mRetrofit.create(ServiceApi.class);
    }

    public ServiceApi getServiceApi() {
        return mServiceApi;
    }



    public interface ServiceApi {

        //@GET("date={time}")
        //Observable<Test> beforeNews(@Path("time") String time);
    }
}

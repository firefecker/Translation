package com.fire.baselibrary.network;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 *
 * @author fire
 * @date 2017/12/28
 * Description:
 */

public class OkhttpClientImpl {

    private static OkhttpClientImpl sMOkhttpClientImpl;
    private CookieManager mCookieManager;
    private OkHttpClient mOkHttpClient;
    private AbstractListener mNetworkListener;
    private Request.Builder mRequestBuilder;

    public static OkhttpClientImpl getInstance() {
        if (sMOkhttpClientImpl == null) {
            synchronized (OkhttpClientImpl.class) {
                if (sMOkhttpClientImpl == null) {
                    sMOkhttpClientImpl = new OkhttpClientImpl();
                }
            }
        }
        return sMOkhttpClientImpl;
    }

    private OkhttpClientImpl() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                message -> Logger.i(message));
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        mCookieManager = new CookieManager();
        mCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(20, TimeUnit.SECONDS)
                .cookieJar(new JavaNetCookieJar(mCookieManager))
                .build();
        mRequestBuilder = new Request.Builder().url(
                "http://open.iciba.com/dsapi/?date=2017-12-26");
    }

    public OkhttpClientImpl get() {
        //可以省略，默认是GET请求
        mRequestBuilder.method("GET", null);
        Request request = mRequestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mNetworkListener == null) {
                    return;
                }
                mNetworkListener.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (mNetworkListener == null) {
                    return;
                }
                //response.body().string()只能调用一次
                String string = response.body().string();
                mNetworkListener.onSuccess(new Gson().fromJson(string, mNetworkListener.getTypeClass()));
            }
        });
        return this;
    }

    public void setOnListener(AbstractListener listener) {
        mNetworkListener = listener;
    }
}

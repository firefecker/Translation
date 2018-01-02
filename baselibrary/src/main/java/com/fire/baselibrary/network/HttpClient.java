package com.fire.baselibrary.network;

/**
 *
 * @author fire
 * @date 2017/12/28
 * Description:
 */

public class HttpClient {
    private static HttpClient mHttpClient;

    public static HttpClient getInstance() {
        if (mHttpClient == null) {
            synchronized (OkhttpClientImpl.class) {
                if (mHttpClient == null) {
                    mHttpClient = new HttpClient();
                }
            }
        }
        return mHttpClient;
    }

    private HttpClient() {
    }
}

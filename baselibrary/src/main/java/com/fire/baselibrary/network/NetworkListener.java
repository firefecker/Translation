package com.fire.baselibrary.network;

/**
 * @author fire
 * @date 2017/12/28
 * Description:
 */

public abstract class NetworkListener<T> {

    private Class<T> tClass;

    public NetworkListener(Class<T> tClass) {
        this.tClass = tClass;
    }

    public abstract void onSucess(T t);
    public abstract void onFailure(Exception e);

    public Class<T> getTypeClass() {
        return tClass;
    }
}

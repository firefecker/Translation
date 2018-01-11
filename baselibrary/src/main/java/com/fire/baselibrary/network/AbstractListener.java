package com.fire.baselibrary.network;

/**
 * @author fire
 * @date 2017/12/28
 * Description:
 */

public abstract class AbstractListener<T> {

    private Class<T> tClass;

    public AbstractListener(Class<T> tClass) {
        this.tClass = tClass;
    }

    /**
     * success
     * @param t
     * @return
     */
    public abstract void onSuccess(T t);

    /**
     * Failure
     * @param e
     * @return
     */
    public abstract void onFailure(Exception e);

    public Class<T> getTypeClass() {
        return tClass;
    }
}

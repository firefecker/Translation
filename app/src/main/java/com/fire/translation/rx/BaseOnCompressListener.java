package com.fire.translation.rx;

import com.fire.baselibrary.utils.ToastUtils;
import java.io.File;
import top.zibin.luban.OnCompressListener;

/**
 * Created by fire on 2018/1/16.
 * Date：2018/1/16
 * Author: fire
 * Description:
 */

public abstract class BaseOnCompressListener implements OnCompressListener {
    /**
     * Fired when the compression is started, override to handle in your own code
     */
    @Override
    public void onStart() {
    }

    /**
     * Fired when a compression returns successfully, override to handle in your own code
     */
    @Override
    public abstract void onSuccess(File file);

    /**
     * Fired when a compression fails to complete, override to handle in your own code
     */
    @Override
    public void onError(Throwable e) {
        ToastUtils.showToast(e.getMessage());
    }
}

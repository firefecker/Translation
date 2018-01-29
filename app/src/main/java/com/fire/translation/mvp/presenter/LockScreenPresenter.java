package com.fire.translation.mvp.presenter;

import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.translation.mvp.model.LockScreenModel;
import com.fire.translation.mvp.view.LockScreenView;

/**
 * Created by fire on 2018/1/29.
 * Date：2018/1/29
 * Author: fire
 * Description:
 */

public class LockScreenPresenter implements IBasePresenter {

    private LockScreenModel mLockScreenModel;
    private LockScreenView mLockScreenView;

    public LockScreenPresenter(LockScreenView lockScreenView) {
        mLockScreenModel = new LockScreenModel();
        mLockScreenView = lockScreenView;
    }

    public void getLocalData() {
        mLockScreenView.localData(mLockScreenModel.getLocalData());
    }
}

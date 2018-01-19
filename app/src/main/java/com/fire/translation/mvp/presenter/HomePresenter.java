package com.fire.translation.mvp.presenter;

import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.translation.db.entities.Record;
import com.fire.translation.mvp.model.HomeModel;
import com.fire.translation.mvp.view.HomeView;

/**
 * Description:
 *
 * @author fire
 */

public class HomePresenter implements IBasePresenter {

    private HomeModel mHomeModel;
    private HomeView mHomeView;

    public HomePresenter(HomeView mHomeView) {
        this.mHomeView = mHomeView;
        this.mHomeModel = new HomeModel();
    }

    public void getDsapi(String date,boolean isShowLoadingView) {
        if (isShowLoadingView) {
            mHomeView.showLoadingView();
        }
        mHomeModel.getDsapi(date)
        .subscribe(test -> {
            //mHomeView.setData(test);
            mHomeView.dismissLoadingView();
        },
        throwable -> {
            mHomeView.notifyError(throwable);
        });
    }

    public void loadRecord() {
        mHomeView.setRecord(mHomeModel.loadRecord());
    }

    public Record getRecord() {
        return mHomeModel.getRecord();
    }

    public void updateJsnum(Record record) {
        mHomeView.updateJsnum(mHomeModel.updateJsnum(record));
    }

    @Override
    public void rxBus(Class mClass,Class aClass) {
        mHomeView.rxBus(mHomeModel.rxBus(mClass).map(eventBase -> {
            if (eventBase.getReceiver() != aClass) {
                return null;
            } else {
                return eventBase;
            }
        }));
    }
}

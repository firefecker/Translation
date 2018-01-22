package com.fire.translation.mvp.presenter;

import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.Word;
import com.fire.translation.mvp.model.RecordModel;
import com.fire.translation.mvp.view.RecordView;
import io.reactivex.functions.Function;
import java.util.List;

/**
 * Created by fire on 2018/1/22.
 * Date：2018/1/22
 * Author: fire
 * Description:
 */

public class RecordPresenter implements IBasePresenter {
    private RecordModel mRecordModel;
    private RecordView mRecordView;

    public RecordPresenter(RecordView mRecordView) {
        this.mRecordView = mRecordView;
        this.mRecordModel = new RecordModel();
    }

    public void loadData() {
        mRecordView.loadData(mRecordModel.loadData());
    }

    public Function<List<Record>, List<Record>> sortData() {
        return mRecordModel.sortData();
    }

    @Override
    public void rxBus(Class mClass, Class aClass) {
        mRecordView.rxBus(mRecordModel.rxBus(mClass).map(eventBase -> eventBase));
    }
}

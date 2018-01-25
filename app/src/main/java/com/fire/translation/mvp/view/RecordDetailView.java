package com.fire.translation.mvp.view;

import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.baselibrary.rx.EventBase;
import com.fire.translation.db.entities.Tanslaterecord;
import com.pushtorefresh.storio3.sqlite.Changes;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResult;
import io.reactivex.Flowable;
import java.util.List;

/**
 *
 * @author fire
 * @date 2018/1/25
 * Description:
 */

public interface RecordDetailView extends IBaseView {
    void loadTranslateRecord(Flowable<Changes> changesFlowable);

    void getAllTranslateRecord(Flowable<List<Tanslaterecord>> allTranslateRecord);

    void starSuccess(EventBase eventBase);

    void starFailure(Throwable throwable);

    void deleteRecord(Flowable<DeleteResult> deleteResultFlowable);
}

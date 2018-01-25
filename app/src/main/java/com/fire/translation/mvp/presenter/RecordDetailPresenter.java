package com.fire.translation.mvp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.translation.R;
import com.fire.translation.db.entities.Tanslaterecord;
import com.fire.translation.mvp.model.RecordDetailModel;
import com.fire.translation.mvp.view.RecordDetailView;

/**
 * @author fire
 * @date 2018/1/25
 * Description:
 */

public class RecordDetailPresenter implements IBasePresenter<Tanslaterecord> {

    private RecordDetailModel mRecordDetailModel;
    private RecordDetailView mRecordDetailView;

    public RecordDetailPresenter(RecordDetailView recordDetailView) {
        mRecordDetailView = recordDetailView;
        mRecordDetailModel = new RecordDetailModel();
    }

    @Override
    public void updateStar(Tanslaterecord tanslaterecord) {
        mRecordDetailModel.updateStar(mRecordDetailView, tanslaterecord);
    }

    public void loadTranslateRecord() {
        mRecordDetailView.loadTranslateRecord(mRecordDetailModel.loadTranslateRecord());
    }

    public void getAllTranslateRecord() {
        mRecordDetailView.getAllTranslateRecord(mRecordDetailModel.getAllTranslateRecord());
    }

    public void deleteRecord(Context context,Tanslaterecord tanslaterecord) {
        new MaterialDialog.Builder(context)
                .title(R.string.notify)
                .content(R.string.delect_record_notify)
                .negativeText(R.string.cancel)
                .positiveText(R.string.comfirm)
                .onPositive((dialog, which) -> mRecordDetailView.deleteRecord(
                        mRecordDetailModel.deleteRecord(tanslaterecord)))
                .build()
                .show();
    }
}

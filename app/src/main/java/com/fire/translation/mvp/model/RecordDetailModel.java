package com.fire.translation.mvp.model;

import com.fire.baselibrary.base.inter.IBaseModel;
import com.fire.baselibrary.rx.EventBase;
import com.fire.translation.R;
import com.fire.translation.db.Dbservice;
import com.fire.translation.db.entities.Tanslaterecord;
import com.fire.translation.mvp.view.RecordDetailView;
import com.fire.translation.ui.fragment.TranslationFragment;
import com.pushtorefresh.storio3.sqlite.Changes;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author fire
 * @date 2018/1/25
 * Description:
 */

public class RecordDetailModel implements IBaseModel {
    public Flowable<Changes> loadTranslateRecord() {
        Set<String> tables = new HashSet<>();
        tables.add(Tanslaterecord.__TABLE__);
        return Dbservice.getInstance()
                .defaultDbConfig()
                .getStorIOSQLite()
                .observeChangesInTables(tables, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .startWith(Changes.newInstance(""));
    }

    public Flowable<List<Tanslaterecord>> getAllTranslateRecord() {
        return Dbservice.getInstance()
                .defaultDbConfig()
                .getAllTranslateRecord()
                .map(tanslaterecords -> {
                    List<Tanslaterecord> list = new ArrayList<>();
                    for (int i = tanslaterecords.size() - 1; i >= 0; i--) {
                        list.add(tanslaterecords.get(i));
                    }
                    return list;
                });
    }

    public void updateStar(RecordDetailView recordDetailView, Tanslaterecord tanslate) {
        Dbservice.getInstance()
                .defaultDbConfig()
                .getCurrentTranslateAsRx(tanslate.getTranslations(),
                        tanslate.getCto(), tanslate.getMfrom(), tanslate.getMquery())
                .map(tanslaterecordOptional -> {
                    Tanslaterecord tanslaterecord = tanslaterecordOptional.get();
                    PutResult putResult = null;
                    if (tanslaterecordOptional.get() != null) {
                        if (tanslaterecord.getStart() == 0) {
                            tanslaterecord.setStart(1);
                        } else {
                            tanslaterecord.setStart(0);
                        }
                        putResult = Dbservice.getInstance()
                                .defaultDbConfig()
                                .insertTanslateRecord(tanslaterecord);
                    }
                    if (putResult == null || !putResult.wasUpdated()) {
                        return EventBase.builder().arg0(tanslaterecord.getStart()).receiver(
                                TranslationFragment.class).arg0(R.string.notify).build();
                    } else {
                        return EventBase.builder().arg0(tanslaterecord.getStart()).receiver(
                                TranslationFragment.class).arg0(R.string.notify).build();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventBase -> {
                    recordDetailView.starSuccess(eventBase);
                }, throwable -> {
                    recordDetailView.starFailure(throwable);
                });
    }

    public  Flowable<DeleteResult> deleteRecord(Tanslaterecord tanslaterecord) {
        return Dbservice.getInstance()
                .defaultDbConfig()
                .deleteTranslateRecord(tanslaterecord);
    }
}

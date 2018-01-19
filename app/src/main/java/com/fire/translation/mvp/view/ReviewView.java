package com.fire.translation.mvp.view;

import android.widget.CheckBox;
import android.widget.Switch;
import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.translation.db.entities.Word;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import io.reactivex.Flowable;
import java.util.List;

/**
 * Created by fire on 2018/1/18.
 * Date：2018/1/18
 * Author: fire
 * Description:
 */

public interface ReviewView extends IBaseView {
    void loadData(Flowable<List<Word>> listFlowable);

    void setUpDateRememberStatus(Switch mSwRemember, Flowable<PutResult> putResultFlowable, boolean status);


    void updateRecordWords(Flowable<PutResult> putResultFlowable);

    void setUpDateNewwordStatus(CheckBox mCbAdd, Flowable<PutResult> putResultFlowable,
            boolean status);
}

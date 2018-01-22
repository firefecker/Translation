package com.fire.translation.mvp.view;

import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.Word;
import io.reactivex.Flowable;
import java.util.List;

/**
 * Created by fire on 2018/1/22.
 * Date：2018/1/22
 * Author: fire
 * Description:
 */

public interface RecordView extends IBaseView {
    void loadData(Flowable<List<Record>> listFlowable);
}

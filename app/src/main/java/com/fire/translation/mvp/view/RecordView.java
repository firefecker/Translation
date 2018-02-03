package com.fire.translation.mvp.view;

import com.fire.baselibrary.base.inter.IBaseView;
import com.fire.translation.db.entities.Record;
import io.reactivex.Flowable;
import java.util.List;

/**
 *
 * @author fire
 * @date 2018/1/22
 * Description:
 */

public interface RecordView extends IBaseView {
    void loadData(Flowable<List<Record>> listFlowable);
}

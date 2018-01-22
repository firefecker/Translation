package com.fire.translation.mvp.model;

import com.fire.baselibrary.base.inter.IBaseModel;
import com.fire.translation.adapter.StickyHeaderAdapter;
import com.fire.translation.constant.Constant;
import com.fire.translation.db.Dbservice;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.Word;
import com.fire.translation.utils.DateUtils;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by fire on 2018/1/22.
 * Date：2018/1/22
 * Author: fire
 * Description:
 */

public class RecordModel implements IBaseModel {
    public Flowable<List<Record>> loadData() {
        return Dbservice.getInstance()
                .defaultDbConfig()
                .getAllRecordData();
    }

    public Function<List<Record>, List<Record>> sortData() {
        return words -> {
            words = new ArrayList<>(words);
            Collections.sort(words, (word1, word2) -> {
                Date date1 = DateUtils.parseToDate(word1.getRecordDate(), DateUtils.dateFormat1);
                Date date2 = DateUtils.parseToDate(word2.getRecordDate(), DateUtils.dateFormat1);
                if (date1.getTime() >= date2.getTime()) {
                    return -1;
                } else if (date1.getTime() == date2.getTime()) {
                    return 0;
                } else {
                    return 1;
                }
            });
            return words;
        };
    }
}

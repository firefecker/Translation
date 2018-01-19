package com.fire.translation.mvp.model;

import com.fire.baselibrary.base.inter.IBaseModel;
import com.fire.translation.constant.Constant;
import com.fire.translation.db.Dbservice;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.Word;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fire on 2018/1/18.
 * Date：2018/1/18
 * Author: fire
 * Description:
 */

public class ReviewModel implements IBaseModel {

    public Flowable<List<Word>> loadData(int reviews) {
        return Flowable.create((FlowableOnSubscribe<List<Word>>) e -> {
            List<Word> list = new ArrayList<>();
            List<Word> rememberWord = Dbservice.getInstance()
                    .setDbConfig(Constant.SQLONENAME)
                    .getCurrentRememberWord(reviews);
            int data = reviews;
            if (rememberWord == null || rememberWord.size() == 0) {
                data = reviews;
            } else {
                list.addAll(rememberWord);
                data = data - rememberWord.size();
            }
            List<Word> currentWord = Dbservice.getInstance()
                    .setDbConfig(Constant.SQLONENAME)
                    .getCurrentWord(data);
            list.addAll(currentWord);
            e.onNext(list);
            e.onComplete();
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<PutResult> setUpDateRememberStatus(Word word) {
        return Dbservice.getInstance()
                .setDbConfig(Constant.SQLONENAME)
                .UpDateWordStatus(word);
    }

    public Flowable<PutResult> updateRecordWords(Record record) {
        return Dbservice.getInstance()
                .defaultDbConfig()
                .updateJsnum(record);
    }
}

package com.fire.translation.mvp.model;

import com.fire.baselibrary.base.inter.IBaseModel;
import com.fire.translation.constant.Constant;
import com.fire.translation.db.Dbservice;
import com.fire.translation.db.entities.Word;
import io.reactivex.Flowable;
import java.util.List;

/**
 * Created by fire on 2018/1/22.
 * Date：2018/1/22
 * Author: fire
 * Description:
 */

public class WordbookModel implements IBaseModel {
    public Flowable<List<Word>> loadData(int newWord,int remomber) {
        return Dbservice.getInstance()
                .setDbConfig(Constant.SQLONENAME)
                .getAllWordData(newWord, remomber);
    }
}

package com.fire.translation.mvp.presenter;

import android.widget.CheckBox;
import android.widget.Switch;
import com.fire.baselibrary.base.inter.IBasePresenter;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.Word;
import com.fire.translation.mvp.model.ReviewModel;
import com.fire.translation.mvp.view.ReviewView;

/**
 * Created by fire on 2018/1/18.
 * Date：2018/1/18
 * Author: fire
 * Description:
 */

public class ReviewPresenter implements IBasePresenter {

    private ReviewModel mReviewModel;
    private ReviewView mReviewView;

    public ReviewPresenter(ReviewView reviewView) {
        mReviewView = reviewView;
        mReviewModel = new ReviewModel();
    }

    public void loadData(int reviews) {
        mReviewView.loadData(mReviewModel.loadData(reviews));
    }

    public void setUpDateRememberStatus(Switch mSwRemember, Word word, boolean status) {
        mReviewView.setUpDateRememberStatus(mSwRemember,mReviewModel.setUpDateRememberStatus(word),status);
    }

    public void updateRecordWords(Record record) {
        mReviewView.updateRecordWords(mReviewModel.updateRecordWords(record));
    }

    public void setUpDateNewwordStatus(CheckBox mCbAdd, Word word, boolean status) {
        mReviewView.setUpDateNewwordStatus(mCbAdd,mReviewModel.setUpDateRememberStatus(word),status);
    }
}

package com.fire.translation.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.fire.translation.R;
import com.fire.translation.db.entities.Word;
import com.fire.translation.mvp.presenter.ReviewPresenter;
import com.fire.translation.utils.DateUtils;
import io.reactivex.functions.Consumer;
import java.util.Date;
import java.util.List;


/**
 * Created by fire on 2018/1/18.
 * Date：2018/1/18
 * Author: fire
 * Description:
 */

public class ReviewAdapter extends PagerAdapter {

    private List<Word> mWords;
    private ReviewPresenter mReviewPresenter;

    public ReviewAdapter(List<Word> words,ReviewPresenter mReviewPresenter) {
        mWords = words;
        this.mReviewPresenter = mReviewPresenter;
    }

    @Override
    public int getCount() {
        return mWords.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.item_review, container,false);

        TextView mTvContent = inflate.findViewById(R.id.tv_content);
        TextView mTvPs = inflate.findViewById(R.id.tv_ps);
        TextView mTvInterpretation = inflate.findViewById(R.id.tv_interpretation);
        TextView mTvEn = inflate.findViewById(R.id.tv_en);
        TextView mTvCn = inflate.findViewById(R.id.tv_cn);
        LinearLayout mLayoutRemember = inflate.findViewById(R.id.layout_remember);
        LinearLayout mLayoutBg = inflate.findViewById(R.id.layout_bg);
        LinearLayout mLayoutAdd = inflate.findViewById(R.id.layout_add);
        Switch mSwRemember = inflate.findViewById(R.id.sw_remember);
        CheckBox mCbAdd = inflate.findViewById(R.id.cb_add);

        mLayoutBg.setVisibility(View.VISIBLE);
        Word word = mWords.get(position);
        mTvContent.setText(word.getWord());
        mTvPs.setText(String.format("[%s]", word.getPs()));
        mTvInterpretation.setText(word.getInterpretation());
        mTvEn.setText(word.getEn());
        mTvCn.setText(word.getCn());
        if (word.getRemember() == 0) {
            mSwRemember.setChecked(false);
        } else {
            mSwRemember.setChecked(true);
        }
        if (word.getNewWord() == 0) {
            mCbAdd.setChecked(false);
        } else {
            mCbAdd.setChecked(true);
        }
        mLayoutAdd.setOnClickListener(v -> {
            Word mWord = word;
            boolean status = false;
            if (mWord.getNewWord() == 0) {
                status = true;
                mWord.setNewWord(1);
                mWord.setTime(DateUtils.formatDateToString(new Date(),DateUtils.dateFormat1));
            } else {
                status = false;
                mWord.setNewWord(0);
                mWord.setTime(DateUtils.formatDateToString(new Date(),DateUtils.dateFormat1));
            }
            mReviewPresenter.setUpDateNewwordStatus(mCbAdd, mWord,status);
        });
        mLayoutBg.setOnClickListener(v -> {
            mLayoutBg.setVisibility(View.GONE);
        });

        mLayoutRemember.setOnClickListener(v -> {
            boolean status = false;
            Word mWord = word;
            if (mWord.getRemember() == 0) {
                status = true;
                mWord.setRemember(1);
                mWord.setTime(DateUtils.formatDateToString(new Date(),DateUtils.dateFormat1));
            } else {
                status = false;
                mWord.setRemember(0);
                mWord.setTime(DateUtils.formatDateToString(new Date(),DateUtils.dateFormat1));
            }
            mReviewPresenter.setUpDateRememberStatus(mSwRemember, mWord,status);
        });
        mTvPs.setOnClickListener(v -> {
            mReviewPresenter.startSpeak(word.getWord());
        });
        container.addView(inflate);
        return inflate;
    }
    public Consumer<List<Word>> asLoadAction() {
        return ts -> {
            mWords.clear();
            mWords.addAll(ts);
            notifyDataSetChanged();
        };
    }

    public int getSize() {
        return mWords.size();
    }
}

package com.fire.translation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.fire.translation.R;
import com.fire.translation.db.entities.Word;
import com.fire.translation.mvp.presenter.WordbookPresenter;
import com.fire.translation.widget.BasesViewHolder;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 *
 * @author fire
 * @date 2018/1/22
 * Description:
 */

public class WordBookAdapter extends EasyRecyclerArrayAdapter<Word> {
    private WordbookPresenter wordbookPresenter;

    public WordBookAdapter(Context context,WordbookPresenter wordbookPresenter) {
        super(context);
        this.wordbookPresenter = wordbookPresenter;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new WordBookViewHolder(parent,wordbookPresenter);
    }
    static class WordBookViewHolder extends BasesViewHolder<Word> {
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_ps)
        TextView mTvPs;
        @BindView(R.id.tv_interpretation)
        TextView mTvInterpretation;
        @BindView(R.id.tv_en)
        TextView mTvEn;
        @BindView(R.id.tv_cn)
        TextView mTvCn;
        @BindView(R.id.layout_second)
        LinearLayout mLayoutSecond;
        @BindView(R.id.layout_show)
        LinearLayout mLayoutShow;
        @BindView(R.id.cb_spread)
        CheckBox mCbSpread;
        @BindView(R.id.iv_star)
        ImageView mIvStar;

        private WordbookPresenter wordbookPresenter;

        public WordBookViewHolder(ViewGroup parent,WordbookPresenter wordbookPresenter) {
            super(parent, R.layout.item_wordbook);
            this.wordbookPresenter = wordbookPresenter;
        }

        @Override
        public void setData(Word data) {
            super.setData(data);
            mTvContent.setText(data.getWord());
            mTvPs.setText(String.format("[%s]", data.getPs()));
            mTvInterpretation.setText(data.getInterpretation());
            mTvEn.setText(String.format("例：%s",data.getEn()));
            mTvCn.setText(String.format("译：%s",data.getCn()));
            mLayoutShow.setOnClickListener(v -> {
                if (!mCbSpread.isChecked()) {
                    mIvStar.startAnimation(wordbookPresenter.getAnimation(itemView.getContext(),R.anim.roate_0_180));
                    mLayoutSecond.setVisibility(View.VISIBLE);
                    mCbSpread.setText("收缩");
                } else {
                    mIvStar.startAnimation(wordbookPresenter.getAnimation(itemView.getContext(),R.anim.roate_180_360));
                    mLayoutSecond.setVisibility(View.GONE);
                    mCbSpread.setText("展开");
                }
                mCbSpread.setChecked(!mCbSpread.isChecked());
            });
            mTvPs.setOnClickListener(v -> {
                wordbookPresenter.startSpeak(data.getWord());
            });
        }
    }
}

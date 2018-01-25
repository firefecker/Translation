package com.fire.translation.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;
import com.fire.translation.R;
import com.fire.translation.db.entities.Tanslaterecord;
import com.fire.translation.mvp.presenter.RecordDetailPresenter;
import com.fire.translation.widget.BasesViewHolder;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fire
 * @date 2018/1/25
 * Description:
 */

public class RecordDetailAdapter extends EasyRecyclerArrayAdapter<Tanslaterecord> implements
        SwipeItemMangerInterface, SwipeAdapterInterface {

    private RecordDetailPresenter mTranslationPresenter;
    private SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public RecordDetailAdapter(Context context,
            List<Tanslaterecord> objects, RecordDetailPresenter mTranslationPresenter) {
        super(context, objects);
        this.mTranslationPresenter = mTranslationPresenter;
    }

    public RecordDetailAdapter(Context context, RecordDetailPresenter mTranslationPresenter) {
        this(context, new ArrayList<>(), mTranslationPresenter);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new TranslateRecordViewHolder(parent, mTranslationPresenter);
    }

    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        mItemManger.closeAllExcept(layout);
    }

    @Override
    public void closeAllItems() {
        mItemManger.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public Attributes.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(Attributes.Mode mode) {
        mItemManger.setMode(mode);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    class TranslateRecordViewHolder extends BasesViewHolder<Tanslaterecord> {

        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_query)
        TextView mTvQuery;
        @BindView(R.id.iv_star)
        ImageView mIvStar;
        @BindView(R.id.swipe)
        SwipeLayout mSwipeLayout;
        @BindView(R.id.tv_delete)
        TextView mTvDelete;
        @BindView(R.id.rl_content)
        RelativeLayout mLayoutContent;

        private RecordDetailPresenter mTranslationPresenter;

        public TranslateRecordViewHolder(ViewGroup parent,
                RecordDetailPresenter mTranslationPresenter) {
            super(parent, R.layout.item_translaterecord1);
            this.mTranslationPresenter = mTranslationPresenter;
        }

        @Override
        public void setData(Tanslaterecord data) {
            super.setData(data);
            if (data == null) {
                return;
            }
            Tanslaterecord tanslaterecord = data;
            mTvContent.setText(tanslaterecord.getTranslations());
            mTvQuery.setText(tanslaterecord.getMquery());
            if (tanslaterecord.getStart() == 1) {
                mIvStar.setImageResource(R.drawable.ic_star);
            } else {
                mIvStar.setImageResource(R.drawable.ic_star1);
            }

            mIvStar.setOnClickListener(v -> {
                mTranslationPresenter.updateStar(tanslaterecord);
            });

            mLayoutContent.setOnClickListener(view -> {
                if (mItemManger.isOpen(getDataPosition())) {
                    mItemManger.closeAllItems();
                    return;
                }
                mItemManger.closeAllItems();
            });
            mTvDelete.setOnClickListener(view -> {
                mItemManger.closeAllItems();
                mTranslationPresenter.deleteRecord(itemView.getContext(),tanslaterecord);
            });
            mItemManger.bindView(itemView, getDataPosition());
        }
    }
}

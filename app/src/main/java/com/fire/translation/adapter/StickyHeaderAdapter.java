package com.fire.translation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fire.translation.R;
import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.Word;
import com.fire.translation.utils.DateUtils;
import com.jude.easyrecyclerview.decoration.StickyHeaderDecoration;
import com.orhanobut.logger.Logger;
import java.util.List;

/**
 * Created by fire on 2018/1/22.
 * Date：2018/1/22
 * Author: fire
 * Description:
 */

public class StickyHeaderAdapter
        implements StickyHeaderDecoration.IStickyHeaderAdapter<StickyHeaderAdapter.HeaderHolder> {

    private LayoutInflater mInflater;
    private List<Record> mList;

    public StickyHeaderAdapter(Context context, List<Record> mList) {
        mInflater = LayoutInflater.from(context);
        this.mList = mList;
    }

    public void setList(List<Record> list) {
        mList = list;
    }

    @Override
    public long getHeaderId(int position) {
        return DateUtils.parseToDate(mList.get(position).getRecordDate(),DateUtils.dateFormat1).getTime();
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_item, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        if (mList.size() != 0) {
            viewholder.header.setText(mList.get(position).getRecordDate());
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView;
        }
    }
}


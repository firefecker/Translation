package com.fire.translation.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.fire.translation.R;
import com.fire.translation.db.entities.Record;
import com.fire.translation.widget.BasesViewHolder;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by fire on 2018/1/22.
 * Date：2018/1/22
 * Author: fire
 * Description:
 */

public class RecordAdapter extends EasyRecyclerArrayAdapter<Record> {

    public RecordAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecordAViewHolder(parent);
    }

    class RecordAViewHolder extends BasesViewHolder<Record> {

        @BindView(R.id.tv_content)
        TextView mtvContent;
        @BindView(R.id.tv_zw)
        TextView mtvZw;
        @BindView(R.id.tv_jc)
        TextView mtvJc;


        public RecordAViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_record);
        }

        @Override
        public void setData(Record data) {
            super.setData(data);
            if (data == null) {
                return;
            }
            mtvContent.setText(String.format("复习次数：%d次",data.getRecordTime()));
            mtvZw.setText(String.format("掌握词数：%d个",data.getRecordWords()));
            mtvJc.setText(String.format("坚持使用：%d天",data.getRecordDays()));
        }
    }
}

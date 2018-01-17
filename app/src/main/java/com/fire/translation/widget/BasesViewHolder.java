package com.fire.translation.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by fire on 2018/1/17.
 * Date：2018/1/17
 * Author: fire
 * Description:
 */

public class BasesViewHolder<T> extends BaseViewHolder<T> {

    public BasesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public BasesViewHolder(ViewGroup parent, int res) {
        this(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
    }
}

package com.fire.translation.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.fire.translation.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */

public class ListPopupWindow extends PopupWindow {

    private View mRootView;
    private ListView mListView;

    private List<String> mStrings = new ArrayList<>();
    private ArrayAdapter<String> dataAdapter;
    private View mShowView;
    private DataBack mDataBack;

    public ListPopupWindow(Context context, int width) {
        this(LayoutInflater.from(context).inflate(R.layout.item_list, null), width, ViewGroup.LayoutParams.WRAP_CONTENT, false);
    }

    @SuppressLint("WrongConstant")
    public ListPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initView(contentView);
        initData();
    }

    private void initData() {
        dataAdapter = new ArrayAdapter<>(mRootView.getContext(), R.layout.popup_text_item, mStrings);
        mListView.setAdapter(dataAdapter);
        mListView.setEmptyView(LayoutInflater.from(getContentView().getContext()).inflate(R.layout.view_empty,mListView,false));
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            dismiss();
            if (mDataBack == null) {
                return;
            }
            String value = (String) parent.getItemAtPosition(position);
            mDataBack.stringBack(mShowView,value);
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mDataBack == null) {
            return;
        }
        mDataBack.dismissBack(mShowView);
    }

    public void setDataBack(DataBack dataBack) {
        mDataBack = dataBack;
    }

    private void initView(View contentView) {
        mRootView = contentView;
        mListView = contentView.findViewById(R.id.listView);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        mShowView = anchor;
    }

    public void setData(List<String> mList) {
        mStrings.clear();
        mStrings.addAll(mList);
        if (dataAdapter == null) {
            return;
        }
        dataAdapter.notifyDataSetChanged();
    }


    public interface DataBack {
        void stringBack (View view,String data);
        void dismissBack (View view);
    }
}

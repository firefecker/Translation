package com.fire.translation.widget;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.fire.translation.R;

/**
 *
 * @author fire
 * @date 2018/1/25
 * Description:
 */

public class AudioDialog extends DialogFragment {
    private View mInflate;
    private ImageView mIvStart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        init();
        mInflate = inflater.inflate(R.layout.item_audio, container,false);
        mIvStart = mInflate.findViewById(R.id.iv_start);
        return mInflate;
    }

    private void init() {
        //无标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //点击边际可消失
        getDialog().setCanceledOnTouchOutside(false);
        Window window = getDialog().getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams
                .FLAG_FULLSCREEN);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        Resources mR = Resources.getSystem();
        lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, mR.getDisplayMetrics());
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.dimAmount = 0.0f;
        window.setAttributes(attributes);
    }
    public void setVoice(int voice) {
        if (mIvStart == null) {
            return;
        }
        if (voice == 0) {
            mIvStart.setImageResource(R.drawable.ic_sort);
        } else if (voice > 0 && voice < 10) {
            mIvStart.setImageResource(R.drawable.ic_sort1);
        } else {
            mIvStart.setImageResource(R.drawable.ic_sort2);
        }
    }
}

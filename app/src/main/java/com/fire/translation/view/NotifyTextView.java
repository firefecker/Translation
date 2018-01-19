package com.fire.translation.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.fire.translation.R;
import com.fire.translation.utils.DisplayUtil;

/**
 * Created by fire on 2018/1/11.
 * Date：2018/1/11
 * Author: fire
 * Description:
 */

public class NotifyTextView extends View {

    private Paint mPaint1,mPaint2;
    private Rect mBounds1,mBounds2;
    private String mLeftText = "0",mRightText = "字";
    private int mLeftColor,mRightColor;
    private float mLeftSize,mRightSize;
    private Resources mR;
    private boolean mShowUnit = true;

    public NotifyTextView(Context context) {
        this(context,null);
    }

    public NotifyTextView(Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NotifyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (context == null) {
            mR = Resources.getSystem();
        } else {
            mR = context.getResources();
        }
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NotifyTextView);
            mLeftSize = array.getDimension(R.styleable.NotifyTextView_leftSize, 20f);
            mRightSize = array.getDimension(R.styleable.NotifyTextView_rightSize, 6f);
            mLeftColor = array.getColor(R.styleable.NotifyTextView_leftColor, Color.BLACK);
            mRightColor = array.getColor(R.styleable.NotifyTextView_rightColor, Color.GRAY);
            mLeftText = array.getString(R.styleable.NotifyTextView_leftText);
            mRightText = array.getString(R.styleable.NotifyTextView_rightText);
            mShowUnit = array.getBoolean(R.styleable.NotifyTextView_showUnit, true);
        } else {
            mLeftSize = 20f;
            mRightSize = 6f;
            mLeftColor = Color.BLACK;
            mRightColor = Color.GRAY;
        }
        if (TextUtils.isEmpty(mLeftText)) {
            mLeftText = "0";
        }
        if (TextUtils.isEmpty(mRightText)) {
            mRightText = "字";
        }
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds1 = new Rect();
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds2 = new Rect();
        init();
    }

    private void init() {
        mPaint1.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mLeftSize, mR.getDisplayMetrics()));
        mPaint2.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mRightSize, mR.getDisplayMetrics()));
        mPaint1.setColor(mLeftColor);
        mPaint1.setTextAlign(Paint.Align.LEFT);
        mPaint1.getTextBounds(mLeftText, 0, mLeftText.length(), mBounds1);
        mPaint2.setColor(mRightColor);
        mPaint2.getTextBounds(mRightText, 0, mRightText.length(), mBounds2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        // 在wrap_content的情况下默认长度为100dp
        float dimension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
                mR.getDisplayMetrics());
        int minSize = DisplayUtil.dip2px(getContext(), dimension);
        // wrap_content的specMode是AT_MOST模式，这种情况下宽/高等同于specSize
        // 查表得这种情况下specSize等同于parentSize，也就是父容器当前剩余的大小
        // 在wrap_content的情况下如果特殊处理，效果等同martch_parent
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minSize, minSize);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minSize, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, minSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mLeftText, getMeasuredWidth() / 2 - mBounds1.width() / 2 - mBounds2.width() / 2, getMeasuredHeight() - 30, mPaint1);
        if (!mShowUnit) {
            return;
        }
        canvas.drawText(mRightText, getMeasuredWidth() / 2 + mBounds1.width() / 2, getMeasuredHeight() - 30, mPaint2);
    }

    public void setData(String leftText,String rightText,int leftColor,int rightColor,float leftSize,float rightSize) {
        mLeftText = leftText;
        mRightText = rightText;
        mLeftColor = leftColor;
        mRightColor = rightColor;
        mLeftSize = leftSize;
        mRightSize = rightSize;
        postInvalidate();
    }

    public void setLeftText(String leftText) {
        mLeftText = leftText;
        postInvalidate();
    }

    public void setRightText(String rightText) {
        mRightText = rightText;
        postInvalidate();
    }

    public void setLeftColor(int leftColor) {
        mLeftColor = leftColor;
        postInvalidate();
    }

    public void setRightColor(int rightColor) {
        mRightColor = rightColor;
        postInvalidate();
    }

    public void setLeftSize(float leftSize) {
        mLeftSize = leftSize;
        postInvalidate();
    }

    public void setRightSize(float rightSize) {
        mRightSize = rightSize;
        postInvalidate();
    }

    @Override
    public void postInvalidate() {
        init();
        super.postInvalidate();
    }

    public String getLeftText() {
        return mLeftText;
    }

    public String getRightText() {
        return mRightText;
    }

    public int getLeftColor() {
        return mLeftColor;
    }

    public int getRightColor() {
        return mRightColor;
    }

    public float getLeftSize() {
        return mLeftSize;
    }

    public float getRightSize() {
        return mRightSize;
    }
}

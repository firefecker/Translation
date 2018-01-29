package com.fire.translation.widget;

import com.fire.translation.view.ShimmerTextView;

/**
 *
 * @author fire
 * @date 2018/1/29
 * Description:
 */

public interface ShimmerViewBase {
    float getGradientX();
    void setGradientX(float gradientX);
    boolean isShimmering();
    void setShimmering(boolean isShimmering);
    boolean isSetUp();
    void setAnimationSetupCallback(ShimmerTextView.ShimmerViewHelper.AnimationSetupCallback callback);
    int getPrimaryColor();
    void setPrimaryColor(int primaryColor);
    int getReflectionColor();
    void setReflectionColor(int reflectionColor);
}

package com.fire.translation.mvp.view;

import android.view.View;
import android.view.animation.Animation;
import com.fire.baselibrary.base.inter.IBaseView;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import java.util.List;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */

public interface TranslationView extends IBaseView{
    void initData(Animation mOperatingAnim1, Animation mOperatingAnim2, Animation mOperatingAnim3,
            List<String> mStrings);

    void translateOnError(TranslateErrorCode translateErrorCode, String s);

    void translateOnResult(Translate translate, String s, String s1);

    void stringBack(View view, String data);

    void dismissBack(View view);
}

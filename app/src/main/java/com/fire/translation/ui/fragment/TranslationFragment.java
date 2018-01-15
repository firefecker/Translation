package com.fire.translation.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.fire.baselibrary.base.BaseFragment;
import com.fire.translation.R;
import com.fire.translation.mvp.presenter.TranslationPresenter;
import com.fire.translation.mvp.view.TranslationView;
import com.fire.translation.rx.DefaultButtonTransformer;
import com.fire.translation.rx.RxBus;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */

public class TranslationFragment extends BaseFragment
        implements TranslationView {

    @BindView(R.id.layout_from)
    LinearLayout mLayoutFrom;
    @BindView(R.id.layout_language)
    LinearLayout mLayoutLanguage;
    @BindView(R.id.layout_to)
    LinearLayout mLayoutTo;
    @BindView(R.id.iv_revise)
    ImageView mIvRevice;
    @BindView(R.id.iv_camera)
    ImageView mIvCamera;
    @BindView(R.id.iv_from)
    ImageView mIvFrom;
    @BindView(R.id.iv_to)
    ImageView mIvTo;
    @BindView(R.id.tv_from)
    TextView mTvFrom;
    @BindView(R.id.tv_to)
    TextView mTvTo;
    @BindView(R.id.et_input)
    EditText mEtInput;

    private Animation mOperatingAnim1;
    private Animation mOperatingAnim2;
    private Animation mOperatingAnim3;
    private List<String> mStrings = new ArrayList<>();
    private TranslationPresenter mTranslationPresenter;
    private String mFilePath;

    @Override
    public int resourceId() {
        return R.layout.fragment_translation;
    }

    @Override
    public void initView() {
        mTranslationPresenter = new TranslationPresenter(this);
        mTranslationPresenter.init(mActivity);
        RxBus.getDefault()
                .toObservable(Uri.class)
                .compose(DefaultButtonTransformer.create())
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    if (uri == null && !TextUtils.isEmpty(mFilePath)) {
                        uri = Uri.parse(mFilePath);
                    }
                    if (uri == null) {
                        return;
                    }
                    Logger.e(uri.getPath());
                }, throwable -> {

                });
    }

    @OnClick({
            R.id.layout_from, R.id.iv_revise, R.id.layout_to,
            R.id.iv_camera, R.id.iv_picture, R.id.iv_gesture, R.id.iv_send
    })
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.layout_from:
                mTranslationPresenter.showPopup(mActivity, mLayoutLanguage,
                        mLayoutFrom, mStrings, mIvFrom, mOperatingAnim1);
                break;
            case R.id.layout_to:
                mTranslationPresenter.showPopup(mActivity, mLayoutLanguage,
                        mLayoutTo, mStrings, mIvTo, mOperatingAnim1);
                break;
            case R.id.iv_revise:
                mIvRevice.startAnimation(mOperatingAnim3);
                String stringFrom = mTvFrom.getText().toString();
                String stringTo = mTvTo.getText().toString();
                mTvFrom.setText(stringTo);
                mTvTo.setText(stringFrom);
                break;
            case R.id.iv_camera:
                mFilePath = mTranslationPresenter.takePhoto(this);
                break;
            case R.id.iv_picture:
                break;
            case R.id.iv_gesture:
                break;
            case R.id.iv_send:
                mTranslationPresenter.translate(mTvFrom.getText().toString(),
                        mTvTo.getText().toString(), mEtInput.getText().toString());
                break;
        }
    }

    @Override
    public void stringBack(View view, String data) {
        switch (view.getId()) {
            case R.id.layout_from:
                mTvFrom.setText(data);
                mIvFrom.startAnimation(mOperatingAnim2);
                break;
            case R.id.layout_to:
                mTvTo.setText(data);
                mIvTo.startAnimation(mOperatingAnim2);
                break;
        }
    }

    @Override
    public void dismissBack(View view) {
        switch (view.getId()) {
            case R.id.layout_from:
                mIvFrom.startAnimation(mOperatingAnim2);
                break;
            case R.id.layout_to:
                mIvTo.startAnimation(mOperatingAnim2);
                break;
        }
    }

    @Override
    public void initData(Animation mOperatingAnim1, Animation mOperatingAnim2,
            Animation mOperatingAnim3, List<String> mStrings) {
        this.mOperatingAnim1 = mOperatingAnim1;
        this.mOperatingAnim2 = mOperatingAnim2;
        this.mOperatingAnim3 = mOperatingAnim3;
        this.mStrings = mStrings;
    }

    @Override
    public void translateOnError(TranslateErrorCode translateErrorCode, String s) {
        Logger.e(translateErrorCode.toString() + "  ======  " + s);
    }

    @Override
    public void translateOnResult(Translate translate, String s, String s1) {
        Logger.e("\ngetDictDeeplink = "
                 + translate.getDictDeeplink()
                 + "\ngetDictWebUrl = "
                 + translate.getDictWebUrl()
                 + "\ngetFrom = "
                 + translate.getFrom()
                 + "\ngetLe = "
                 + translate.getLe()
                 + "\ngetPhonetic = "
                 + translate.getPhonetic()
                 + "\ngetQuery = "
                 + translate.getQuery()
                 + "\ngetTo = "
                 + translate.getTo()
                 + "\ngetUkPhonetic = "
                 + translate.getUkPhonetic()
                 + "\ngetUsPhonetic = "
                 + translate.getUsPhonetic()
                 + "\ngetExplains = "
                 + translate.getExplains()
                 + "\ngetTranslations = "
                 + translate.getTranslations()
                 + "\ngetWebExplains = "
                 + translate.getWebExplains()
                 + "\ngetWebExplains = "
                 + translate.getWebExplains()
                 + "\ns ======  "
                 + s
                 + "\ns1======  "
                 + s1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.e(mFilePath);
        super.onActivityResult(requestCode, resultCode, data);
    }
}

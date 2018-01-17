package com.fire.translation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.fire.baselibrary.base.BaseFragment;
import com.fire.baselibrary.utils.CustomerDialog;
import com.fire.translation.R;
import com.fire.translation.constant.Constant;
import com.fire.translation.mvp.presenter.TranslationPresenter;
import com.fire.translation.mvp.view.TranslationView;
import com.fire.translation.utils.FunctionUtils;
import com.fire.translation.widget.EventBase;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.youdao.ocr.online.Line;
import com.youdao.ocr.online.OCRResult;
import com.youdao.ocr.online.OcrErrorCode;
import com.youdao.ocr.online.Region;
import com.youdao.ocr.online.Word;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    @Override
    public int resourceId() {
        return R.layout.fragment_translation;
    }

    @Override
    protected void onFragmentCreate(@Nullable Bundle paramBundle) {
        mTranslationPresenter = new TranslationPresenter(this);
        mTranslationPresenter.rxBus(EventBase.class);
    }

    @Override
    public void initView() {
        mTranslationPresenter.init(mActivity);
    }

    @OnClick({
            R.id.layout_from, R.id.iv_revise, R.id.layout_to,
            R.id.iv_camera, R.id.iv_picture, R.id.iv_gesture, R.id.iv_send
    })
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.layout_from:
                mTranslationPresenter.showPopup(mActivity, mLayoutLanguage, mLayoutFrom, mStrings,
                        mIvFrom, mOperatingAnim1);
                break;
            case R.id.layout_to:
                mTranslationPresenter.showPopup(mActivity, mLayoutLanguage, mLayoutTo, mStrings,
                        mIvTo, mOperatingAnim1);
                break;
            case R.id.iv_revise:
                mIvRevice.startAnimation(mOperatingAnim3);
                String stringFrom = mTvFrom.getText().toString();
                String stringTo = mTvTo.getText().toString();
                mTvFrom.setText(stringTo);
                mTvTo.setText(stringFrom);
                break;
            case R.id.iv_camera:
                mActivity.startActivityForResult(FunctionUtils.getNormalPictureIntent(mActivity),
                        Constant.ACTION_IMAGE);
                break;
            case R.id.iv_picture:
                mActivity.startActivityForResult(FunctionUtils.getAlbumIntent(),  Constant.ACTION_ALBUM);
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
    public void ocrOnResult(Observable<OCRResult> ocrResultObservable) {
        //图片识别成功
        ocrResultObservable
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    CustomerDialog.dismissProgress();
                    List<Region> regions = result.getRegions();
                    StringBuilder sb = new StringBuilder();
                    sb.append("识别结果:" + (result.getErrorCode() == 0 ? "成功" : "识别异常"));
                    sb.append("\n");
                    for (Region region : regions) {
                        List<Line> lines = region.getLines();
                        for (Line line : lines) {
                            List<Word> words = line.getWords();
                            for (Word word : words) {
                                sb.append(word.getText()).append(" ");
                            }
                            sb.append("\n");
                        }
                        sb.append("\n");
                    }
                    Logger.e(sb.toString());
                }, throwable -> CustomerDialog.dismissProgress());
    }

    @Override
    public void ocrOnError(Observable<OcrErrorCode> ocrResultObservable) {
        //图片识别失败
        ocrResultObservable
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translate -> {
                    CustomerDialog.dismissProgress();
                    Logger.e("message = "
                             + translate.getCode()
                             + ",  name = "
                             + translate.toString());
                }, throwable -> CustomerDialog.dismissProgress());
    }

    @Override
    public void rxBus(Observable<EventBase> observable) {
        observable.compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventBase -> {
                    if (eventBase != null) {
                        CustomerDialog.showProgress(mActivity);
                        mTranslationPresenter.zipFile(eventBase,mActivity);
                    }
                }, throwable -> {
                    Logger.e(throwable.toString());
                });
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
    public void translateOnError(Observable<TranslateErrorCode> translateErrorCode) {
        //翻译失败
        translateErrorCode
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translate -> {
                    CustomerDialog.dismissProgress();
                    Logger.e(translate.toString());
                }, throwable -> CustomerDialog.dismissProgress());
    }

    @Override
    public void translateOnResult(Observable<Translate> translateObservable) {
        //翻译成功
        translateObservable
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translate -> {
                    CustomerDialog.dismissProgress();
                    Logger.e("getWebExplains = "
                             + translate.getWebExplains()
                             + "\ngetTranslations = "
                             + translate.getTranslations()
                             + "\ngetExplains = "
                             + translate.getExplains()
                             + "\ngetUsPhonetic = "
                             + translate.getUsPhonetic()
                             + "\ngetUkPhonetic = "
                             + translate.getUkPhonetic()
                             + "\ngetTo = "
                             + translate.getTo()
                             + "\ngetQuery = "
                             + translate.getQuery()
                             + "\ngetPhonetic = "
                             + translate.getPhonetic()
                             + "\ngetLe = "
                             + translate.getLe()
                             + "\ngetFrom = "
                             + translate.getFrom()
                             + "\ngetDictWebUrl = "
                             + translate.getDictWebUrl()
                             + "\ngetDeeplink = "
                             + translate.getDeeplink()
                             + "\ngetDictDeeplink = "
                             + translate.getDictDeeplink()
                             + "\ngetErrorCode = "
                             + translate.getErrorCode());
                }, throwable -> CustomerDialog.dismissProgress());
    }
}

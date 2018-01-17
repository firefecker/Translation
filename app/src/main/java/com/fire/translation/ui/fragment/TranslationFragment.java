package com.fire.translation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.fire.baselibrary.base.BaseFragment;
import com.fire.baselibrary.utils.CustomerDialog;
import com.fire.baselibrary.utils.ToastUtils;
import com.fire.translation.R;
import com.fire.translation.adapter.TranslateRecordAdapter;
import com.fire.translation.constant.Constant;
import com.fire.translation.db.entities.Tanslaterecord;
import com.fire.translation.mvp.presenter.TranslationPresenter;
import com.fire.translation.mvp.view.TranslationView;
import com.fire.translation.rx.DefaultButtonTransformer;
import com.fire.translation.rx.RxRvAdapterView;
import com.fire.translation.utils.FileUtils;
import com.fire.translation.utils.FunctionUtils;
import com.fire.translation.widget.EventBase;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.orhanobut.logger.Logger;
import com.pushtorefresh.storio3.sqlite.Changes;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.youdao.ocr.online.OCRResult;
import com.youdao.ocr.online.OcrErrorCode;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */

public class TranslationFragment extends BaseFragment implements TranslationView {

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
    @BindView(R.id.iv_send)
    ImageView mIvSend;
    @BindView(R.id.tv_from)
    TextView mTvFrom;
    @BindView(R.id.tv_to)
    TextView mTvTo;
    @BindView(R.id.et_input)
    EditText mEtInput;
    @BindView(R.id.tv_volume)
    TextView mTvVolume;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_content2)
    TextView mTvContent2;
    @BindView(R.id.iv_copy)
    ImageView mIvCopy;
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.iv_star)
    ImageView mIvStar;
    @BindView(R.id.layout_result)
    RelativeLayout mLayoutResult;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private Animation mOperatingAnim1;
    private Animation mOperatingAnim2;
    private Animation mOperatingAnim3;
    private List<String> mStrings = new ArrayList<>();
    private TranslationPresenter mTranslationPresenter;
    private Tanslaterecord mListTranslate;
    private TranslateRecordAdapter mRecordAdapter;

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
        mRecordAdapter = new TranslateRecordAdapter(mActivity, new ArrayList<>());
        mRecyclerView.setAdapter(mRecordAdapter);
        mTranslationPresenter.init(mActivity);
        mTranslationPresenter.loadTranslateRecord();
        RxTextView.textChanges(mTvTo)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> mTvVolume.setText(charSequence),
                        throwable -> {
                        });
        RxTextView.textChanges(mEtInput)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {
                            if (TextUtils.isEmpty(charSequence.toString().trim())) {
                                mRecyclerView.setVisibility(View.VISIBLE);
                                mLayoutResult.setVisibility(View.GONE);
                            }
                        }, throwable -> {});
        RxRvAdapterView.itemClicks(mRecordAdapter)
                .compose(DefaultButtonTransformer.create())
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(integer -> {
                    mEtInput.setText(mRecordAdapter.getAllData().get(integer).getTranslations());
                    mIvSend.performClick();
                });
    }

    @OnClick({
            R.id.layout_from, R.id.iv_revise, R.id.layout_to,
            R.id.iv_copy, R.id.tv_volume, R.id.iv_camera, R.id.iv_star,
            R.id.iv_picture, R.id.iv_gesture, R.id.iv_send, R.id.iv_more
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
                mActivity.startActivityForResult(FunctionUtils.getAlbumIntent(),
                        Constant.ACTION_ALBUM);
                break;
            case R.id.iv_copy:
                FileUtils.copy(mTvContent.getText());
                break;
            case R.id.iv_gesture://手势
                break;
            case R.id.tv_volume://发音
                break;
            case R.id.iv_more://更多
                break;
            case R.id.iv_star:
                mTranslationPresenter.updateStar(mListTranslate);
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
                .map(mTranslationPresenter.setOcrResult())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    setData(result,"");
                }, throwable -> CustomerDialog.dismissProgress());
    }

    @Override
    public void ocrOnError(Observable<OcrErrorCode> ocrResultObservable) {
        //图片识别失败
        ocrResultObservable
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translate -> {
                    setData("识别失败","");
                }, throwable -> CustomerDialog.dismissProgress());
    }

    @Override
    public void rxBus(Observable<EventBase> observable) {
        observable.compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventBase -> {
                    if (eventBase != null) {
                        CustomerDialog.showProgress(mActivity);
                        mTranslationPresenter.zipFile(eventBase, mActivity);
                    }
                }, throwable -> {
                    Logger.e(throwable.toString());
                });
    }

    @Override
    public void starFailure(Throwable throwable) {
        ToastUtils.showToast(throwable.getMessage());
    }

    @Override
    public void starSuccess(EventBase eventBase) {
        mTranslationPresenter.getAllTranslateRecord();
        if (mIvStar.getVisibility() == View.GONE) {
            mIvStar.setVisibility(View.VISIBLE);
        }
        if (eventBase.getArg0() == 1) {
            mIvStar.setImageResource(R.drawable.ic_star);
        } else {
            mIvStar.setImageResource(R.drawable.ic_star_border);
        }
    }

    @Override
    public void loadTranslateRecord(Flowable<Changes> changesFlowable) {
        changesFlowable.compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(changes -> mTranslationPresenter.getAllTranslateRecord());
    }

    @Override
    public void getAllTranslateRecord(Flowable<List<Tanslaterecord>> allTranslateRecord) {
        allTranslateRecord.compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mRecordAdapter.asLoadAction(), throwable -> {
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
                    setData("翻译失败","");
                }, throwable -> CustomerDialog.dismissProgress());
    }

    private void setData(String s, String s1) {
        CustomerDialog.dismissProgress();
        Logger.e(s.toString());
        mRecyclerView.setVisibility(View.GONE);
        mLayoutResult.setVisibility(View.VISIBLE);
        mTvContent.setText(s);
        mTvContent2.setText(s1);
    }

    @Override
    public void translateOnResult(Observable<Translate> translateObservable) {
        //翻译成功
        translateObservable
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .map(mTranslationPresenter.settranslateResult())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translate -> {
                    mListTranslate = translate;
                    starSuccess(EventBase.builder().arg0(translate.getStart()).build());
                    setData(mListTranslate.getTranslations(),mListTranslate.getExplains());
                }, throwable -> CustomerDialog.dismissProgress());
    }
}

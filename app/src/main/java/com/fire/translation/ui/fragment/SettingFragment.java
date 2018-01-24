package com.fire.translation.ui.fragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.support.annotation.Nullable;
import com.fire.baselibrary.base.BasePreferenceFragment;
import com.fire.baselibrary.rx.EventBase;
import com.fire.baselibrary.rx.RxBus;
import com.fire.baselibrary.utils.CustomerDialog;
import com.fire.baselibrary.utils.ListUtils;
import com.fire.baselibrary.utils.ToastUtils;
import com.fire.translation.R;
import com.fire.translation.TransApplication;
import com.fire.translation.constant.Constant;
import com.fire.translation.db.Dbservice;
import com.fire.translation.db.entities.TableName;
import com.fire.translation.mvp.presenter.SettingPresenter;
import com.fire.translation.mvp.view.SettingView;
import com.fire.translation.network.RetrofitClient;
import com.fire.translation.utils.CacheUtils;
import com.fire.translation.utils.FileUtils;
import com.fire.translation.utils.IntentUtils;
import com.orhanobut.logger.Logger;
import com.pushtorefresh.storio3.Optional;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.Set;

/**
 * Description:
 *
 * @author fire
 */

public class SettingFragment extends BasePreferenceFragment implements SettingView {

    private ListPreference mStudyPlan;
    private ListPreference mWordPlan;
    private ListPreference mSortPlan;
    private MultiSelectListPreference mWordSelect;
    private MultiSelectListPreference mLanguageSelect;
    private Preference mClearCache;
    private Preference mShare;
    private Preference mSupportDeveloper;
    private Preference mYuewen;
    private Preference mZhihu;

    private SettingPresenter mSettingPresenter;

    @Override
    public int resourceId() {
        return R.xml.fragment_mine;
    }

    @Override
    public void initView() {
        mStudyPlan = (ListPreference) findPreference("study_plan");
        mWordPlan = (ListPreference) findPreference("word_plan");
        mSortPlan = (ListPreference) findPreference("sort_plan");
        mWordSelect = (MultiSelectListPreference) findPreference("word_select");
        mLanguageSelect = (MultiSelectListPreference) findPreference("language_select");
        mClearCache = findPreference("clear_cache");
        mShare = findPreference("share");
        mSupportDeveloper = findPreference("support_developer");
        mZhihu = findPreference("zhihu");
        mYuewen = findPreference("yuewen");
    }

    @Override
    protected void initData() {
        mClearCache.setSummary(CacheUtils.getTotalCacheSize(TransApplication.mTransApp));
        initPlan(R.array.wordselect, R.array.wordselect_value, mWordSelect.getValues(),
                mWordSelect);
        initPlan(R.array.plan, R.array.languages, mLanguageSelect.getValues(), mLanguageSelect);
        initPlan(R.array.sort, R.array.sort_value, mSortPlan.getValue(), mSortPlan);
        initPlan(R.array.plan, R.array.plan_value, mStudyPlan.getValue(), mStudyPlan);
        initPlan(R.array.newword, R.array.newword_value, mWordPlan.getValue(), mWordPlan);

        mStudyPlan.setOnPreferenceChangeListener((preference, newValue) -> {
            initPlan(R.array.plan, R.array.plan_value, (String) newValue, preference);
            mSettingPresenter.getTableName(preference.getSummary().toString());
            return true;
        });
        mWordPlan.setOnPreferenceChangeListener((preference, newValue) -> {
            RxBus.getDefault()
                .post(EventBase.builder()
                    .receiver(HomeFragment.class)
                    .arg2(getString(R.string.wordplan))
                    .arg3(ListUtils.stringToString(getActivity(), R.array.newword,
                            R.array.newword_value, (String) newValue))
                    .build());
            initPlan(R.array.newword, R.array.newword_value, (String) newValue, preference);
            return true;
        });
        mSortPlan.setOnPreferenceChangeListener((preference, newValue) -> {
            Constant.SORT = ListUtils.stringToString(getActivity(), R.array.sort,
                    R.array.sort_value, (String) newValue);
            initPlan(R.array.sort, R.array.sort_value, (String) newValue, preference);
            return true;
        });

        mWordSelect.setOnPreferenceChangeListener((preference, newValue) -> {
            RxBus.getDefault()
                    .post(EventBase.builder()
                            .receiver(WordbookFragment.class)
                            .arg2(getString(R.string.wordselect))
                            .build());
            initPlan(R.array.wordselect, R.array.wordselect_value, (Set<String>) newValue,
                    preference);
            return true;
        });

        mLanguageSelect.setOnPreferenceChangeListener((preference, newValue) -> {
            RxBus.getDefault()
                    .post(EventBase.builder()
                            .receiver(TranslationFragment.class)
                            .arg2(getString(R.string.language))
                            .build());
            initPlan(R.array.plan, R.array.languages, (Set<String>) newValue, preference);
            return true;
        });

        mShare.setOnPreferenceClickListener(preference -> {
            startActivity(IntentUtils.openSystemShare(getString(R.string.share_content)));
            return true;
        });

        mClearCache.setOnPreferenceClickListener(preference -> {
            if ("0k".equals(preference.getSummary())) {
                ToastUtils.showToast(getString(R.string.no_cache));
                return true;
            }
            Observable.create(e -> {
                CacheUtils.clearAllCache(TransApplication.mTransApp);
                e.onNext(new Object());
                e.onComplete();
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aVoid -> ToastUtils.showToast(getString(R.string.clear_cache)),
                            throwable -> Logger.e(throwable.toString(), (Action) () -> {
                                mClearCache.setSummary(String.format("缓存大小：%s",
                                        CacheUtils.getTotalCacheSize(TransApplication.mTransApp)));
                            }));
            return true;
        });

        mSupportDeveloper.setOnPreferenceClickListener(preference -> {
            if (IntentUtils.hasInstalledAlipayClient(getActivity())) {
                IntentUtils.startAlipayClient(getActivity(), getString(R.string.alipay_urlcode));
            } else {
                ToastUtils.showToast(getString(R.string.alipay_notify));
            }
            return true;
        });

        mZhihu.setOnPreferenceClickListener(preference -> {
            return true;
        });

        mYuewen.setOnPreferenceClickListener(preference -> {
            return true;
        });
    }

    @Override
    protected void onFragmentCreate(@Nullable Bundle paramBundle) {
        super.onFragmentCreate(paramBundle);
        mSettingPresenter = new SettingPresenter(this);
    }

    @Override
    public void getTableName(Flowable<Optional<TableName>> optionalFlowable) {
        optionalFlowable.compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .map(tableNameOptional -> tableNameOptional.get())
                .subscribe(tableName -> {
                    if (tableName == null) {
                        ToastUtils.showToast("该类型的数据库不存在");
                        return;
                    }
                    mSettingPresenter.setData(tableName,TransApplication.mTransApp);
                },throwable -> Logger.e(throwable.toString()));
    }

    @Override
    public void downloadData(String name,String tableName) {
        String mName = name;
        String mTableName = tableName;
        RetrofitClient.getInstance()
                .setUrl(Constant.DOWNLOADBASE_URL)
                .getServiceApi()
                .downloadZip(mName)
                .subscribeOn(Schedulers.io())
                .map(responseBody -> FileUtils.writeResponseBodyToDisk(getActivity(), responseBody,
                        mName))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aboolean -> {
                    dismissLoadingView();
                    if (aboolean) {
                        ToastUtils.showToast("下载成功");
                        FileUtils.unZip(new File(TransApplication.mTransApp.getFilesDir() + File.separator + mName), TransApplication.mTransApp.getDatabasePath(".").getAbsolutePath());
                        mSettingPresenter.updateDataBase(mTableName,String.format("%s.db",mTableName));
                    } else {
                        ToastUtils.showToast("下载失败");
                    }
                },throwable -> {
                    Logger.e(throwable.toString());
                    dismissLoadingView();
                });
    }

    @Override
    public void updateDataBase(String mTableName,String mName) {
        CustomerDialog.dismissProgress();
        Constant.SQLONENAME = mName;
        Constant.SQLTYPE = mTableName;
        TransApplication.mTransApp.initDBHelper(mName);
        Dbservice.getInstance()
                .setDbConfig(Constant.SQLONENAME);
        RxBus.getDefault()
                .post(EventBase.builder()
                        .receiver(HomeFragment.class)
                        .arg2("delete")
                        .arg0(0)
                        .build());
    }

    @Override
    public void showLoadingView() {
        CustomerDialog.showProgress(getActivity());
    }

    @Override
    public void dismissLoadingView() {
        CustomerDialog.dismissProgress();
    }
}

package com.fire.translation.ui.fragment;

import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import com.fire.baselibrary.base.BasePreferenceFragment;
import com.fire.baselibrary.rx.EventBase;
import com.fire.baselibrary.rx.RxBus;
import com.fire.baselibrary.utils.ListUtils;
import com.fire.baselibrary.utils.ToastUtils;
import com.fire.translation.R;
import com.fire.translation.TransApplication;
import com.fire.translation.constant.Constant;
import com.fire.translation.utils.CacheUtils;
import com.fire.translation.utils.IntentUtils;
import com.orhanobut.logger.Logger;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import java.util.Set;

/**
 * Description:
 *
 * @author fire
 */

public class MineFragment extends BasePreferenceFragment {

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
}

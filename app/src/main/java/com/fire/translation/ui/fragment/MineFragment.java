package com.fire.translation.ui.fragment;

import android.preference.ListPreference;
import android.preference.Preference;
import android.support.annotation.ArrayRes;

import com.fire.baselibrary.base.BasePreferenceFragment;
import com.fire.translation.R;

/**
 * Description:
 * @author fire
 */

public class MineFragment  extends BasePreferenceFragment {

    private ListPreference mStudyPlan;
    private ListPreference mWordPlan;
    private ListPreference mSortPlan;

    @Override
    public int resourceId() {
        return R.xml.fragment_mine;
    }

    @Override
    public void initView() {
        mStudyPlan = (ListPreference) findPreference("study_plan");
        mWordPlan = (ListPreference) findPreference("word_plan");
        mSortPlan = (ListPreference) findPreference("sort_plan");
        initPlan(R.array.sort,R.array.sort_value,mSortPlan.getValue(),mSortPlan);
        initPlan(R.array.plan,R.array.plan_value,mStudyPlan.getValue(),mStudyPlan);
        initPlan(R.array.newword,R.array.newword_value,mWordPlan.getValue(),mWordPlan);
        mStudyPlan.setOnPreferenceChangeListener((preference, newValue) -> {
            initPlan(R.array.plan,R.array.plan_value,(String) newValue,mStudyPlan);
            return true;
        });
        mWordPlan.setOnPreferenceChangeListener((preference, newValue) -> {
            initPlan(R.array.newword,R.array.newword_value,(String) newValue,mWordPlan);
            return true;
        });
        mSortPlan.setOnPreferenceChangeListener((preference, newValue) -> {
            initPlan(R.array.sort,R.array.sort_value,(String) newValue,mSortPlan);
            return true;
        });

    }

    private void initPlan(@ArrayRes int array, @ArrayRes int arrayValue, String newValue, Preference perference) {
        String[] stringArray = getResources().getStringArray(array);
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(newValue)) {
                perference.setSummary(getResources().getStringArray(arrayValue)[i]);
            }
        }
    }
}

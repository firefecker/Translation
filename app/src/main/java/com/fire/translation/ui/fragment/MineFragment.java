package com.fire.translation.ui.fragment;

import android.preference.ListPreference;
import android.preference.Preference;
import com.fire.baselibrary.base.BasePreferenceFragment;
import com.fire.translation.R;
import com.fire.translation.R2;

/**
 * Description:
 * @author fire
 */

public class MineFragment  extends BasePreferenceFragment {

    private ListPreference mStudyPlan;

    @Override
    public int resourceId() {
        return R.xml.fragment_mine;
    }

    @Override
    public void initView() {
        mStudyPlan = (ListPreference) findPreference("study_plan");
        initStudyPlan(mStudyPlan.getValue());
        mStudyPlan.setOnPreferenceChangeListener((preference, newValue) -> {
            initStudyPlan((String) newValue);
            return true;
        });
    }

    private void initStudyPlan(String newValue) {
        String[] stringArray = getResources().getStringArray(R.array.plan);
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(newValue)) {
                mStudyPlan.setSummary(getResources().getStringArray(R.array.plan_value)[i]);
            }
        }
    }
}

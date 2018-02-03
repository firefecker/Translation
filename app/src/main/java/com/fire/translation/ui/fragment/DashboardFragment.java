package com.fire.translation.ui.fragment;

import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.fire.baselibrary.base.BaseFragment;
import com.fire.translation.R;
import com.fire.translation.adapter.PagerFragmentAdapter;
import com.fire.translation.ui.activity.MainActivity;

/**
 * Description:
 * @author fire
 */

public class DashboardFragment extends BaseFragment {

    @BindView(R.id.viewPager)
    public ViewPager mViewPager;

    private PagerFragmentAdapter mFragmentAdapter;
    private TranslationFragment mTranslationFragment;
    private RecordFragment mRecordFragment;
    private WordbookFragment mWordbookFragment;

    @Override
    public int resourceId() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void initView() {
        mTranslationFragment = new TranslationFragment();
        mRecordFragment = new RecordFragment();
        mWordbookFragment = new WordbookFragment();
        mFragmentAdapter = new PagerFragmentAdapter(getChildFragmentManager());
        mFragmentAdapter.addFragment(mTranslationFragment, getString(R.string.translation));
        mFragmentAdapter.addFragment(mRecordFragment, getString(R.string.record));
        mFragmentAdapter.addFragment(mWordbookFragment, getString(R.string.word_book));
        mViewPager.setAdapter(mFragmentAdapter);
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.mTabLayout.setupWithViewPager(mViewPager);
            //mainActivity.setIndicator();
        }
    }
}

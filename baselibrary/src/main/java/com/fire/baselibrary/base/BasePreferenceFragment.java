package com.fire.baselibrary.base;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.ArrayRes;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;
import android.view.View;
import com.fire.baselibrary.utils.ListUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by fire on 2018/1/22.
 * Date：2018/1/22
 * Author: fire
 * Description:
 */

public abstract class BasePreferenceFragment extends PreferenceFragment  implements
        LifecycleProvider<FragmentEvent> {

    public Activity mActivity;

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
        //从xml文件加载选项
        setHasOptionsMenu(true);
        addPreferencesFromResource(resourceId());
        onFragmentCreate(savedInstanceState);
        initView();
        initData();

    }

    protected abstract void initData();

    public abstract @XmlRes
    int resourceId ();

    public abstract void initView ();

    /**
     * 实现父类的onActivityCreate，完成Presenter的生成以及P和V的绑定
     * @param paramBundle
     */
    protected void onFragmentCreate(@Nullable Bundle paramBundle) {

    }

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    @CallSuper
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }


    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    @CallSuper
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    @CallSuper
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    public void initPlan(@ArrayRes int array, @ArrayRes int arrayValue, String newValue,
            Preference perference) {
        String[] stringArray = getResources().getStringArray(array);
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(newValue)) {
                perference.setSummary(getResources().getStringArray(arrayValue)[i]);
            }
        }
    }

    public void initPlan(@ArrayRes int array, @ArrayRes int arrayValue, Set<String> newValue,
            Preference perference) {
        String[] stringArray = getResources().getStringArray(array);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            for (String s : newValue) {
                if (stringArray[i].equals(s)) {
                    list.add(getResources().getStringArray(arrayValue)[i]);
                }
            }
        }
        perference.setSummary(ListUtils.listStrToString(list, ","));
    }
}

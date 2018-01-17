package com.fire.translation.adapter;

import android.content.Context;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import io.reactivex.functions.Consumer;
import java.util.List;

/**
 * Created by fire on 2018/1/17.
 * Date：2018/1/17
 * Author: fire
 * Description:
 */

public abstract class EasyRecyclerArrayAdapter<T> extends RecyclerArrayAdapter<T> {

    public EasyRecyclerArrayAdapter(Context context) {
        super(context);
    }

    public EasyRecyclerArrayAdapter(Context context, T[] objects) {
        super(context, objects);
    }

    public EasyRecyclerArrayAdapter(Context context, List<T> objects) {
        super(context, objects);
    }

    public Consumer<List<T>> asLoadAction() {
        return ts -> {
            clear();
            addAll(ts);
        };
    }

    @Deprecated
    public static Consumer<Throwable> asErrorAction(final EasyRecyclerView easyRecyclerView) {
        return throwable -> easyRecyclerView.showError();
    }

}


package com.ams.androiddevkit.baseClasses.recyclerView;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

public abstract class BaseViewHolder<T, RVListener> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View view) {
        super(view);
        bindViews(view);
    }

    @SuppressWarnings("WeakerAccess")
    protected void bindViews(View view) { ButterKnife.bind(this, view); }

    /**
     * Bind data to the item and set listener if needed.
     *
     * @param item     object, associated with the item.
     * @param listener listener a listener {@link BaseRecyclerViewListener} which has to b set at the item (if not `null`).
     */
    @SuppressWarnings("WeakerAccess")
    protected abstract void onBind(T item, @Nullable RVListener listener);
}

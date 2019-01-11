package ams.android_base.baseClasses.recyclerView;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

public abstract class BaseRecyclerViewHolder<T, L> extends RecyclerView.ViewHolder {

    public BaseRecyclerViewHolder(View view) {
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
    public abstract void onBind(T item, @Nullable L listener);

}

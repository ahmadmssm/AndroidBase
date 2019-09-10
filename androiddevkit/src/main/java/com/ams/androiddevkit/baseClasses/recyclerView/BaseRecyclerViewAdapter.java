package com.ams.androiddevkit.baseClasses.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Base generic RecyclerView adapter.
 * Handles basic logic such as adding/removing items,
 * setting listener, binding ViewHolders.
 * Extend the adapter for appropriate use case.
 *
 * @param <T>  type of objects, which will be used in the adapter's dataset
 * @param <RVListener>  click listener {@link BaseRecyclerViewListener}
 * @param <VH> ViewHolder {@link BaseViewHolder}
 */
@SuppressWarnings("unused")
public abstract class BaseRecyclerViewAdapter<T, RVListener extends BaseRecyclerViewListener, VH extends BaseViewHolder<T, RVListener>> extends RecyclerView.Adapter<VH> {

    private List<T> items;
    private RVListener listener;
    private LayoutInflater layoutInflater;

    /**
     * Base constructor.
     * Allocate adapter-related objects here if needed.
     *
     * @param context Context needed to retrieve LayoutInflater
     */
    public BaseRecyclerViewAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        items = new ArrayList<>();
    }

    /**
     * To be implemented in as specific adapter
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the itemView to reflect the item at the given
     * position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T item = items.get(position);
        holder.onBind(item, listener);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    /**
     * Sets items to the adapter and notifies that data set has been changed.
     *
     * @param items items to set to the adapter
     * @throws IllegalArgumentException in case of setting `null` items
     */
    public void setItems(List<T> items) {
        if (items == null) {
            throw new IllegalArgumentException("Cannot set `null` item to the Recycler adapter");
        }
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Returns getAllMatches items from the data set held by the adapter.
     *
     * @return All of items in this adapter.
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Returns an items from the data set at a certain position.
     *
     * @return All of items in this adapter.
     */
    public T getItem(int position) {
        return items.get(position);
    }

    /**
     * Adds item to the end of the data set.
     * Notifies that item has been inserted.
     *
     * @param item item which has to be added to the adapter.
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item to the Recycler adapter");
        }
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    /**
     * Adds list of items to the end of the adapter's data set.
     * Notifies that item has been inserted.
     *
     * @param items items which has to be added to the adapter.
     */
    public void addAll(List<T> items) {
        if (items == null) {
            throw new IllegalArgumentException("Cannot add `null` items to the Recycler adapter");
        }
        this.items.addAll(items);
        notifyItemRangeInserted(this.items.size() - items.size(), items.size());
    }

    /**
     * Clears getAllMatches the items in the adapter.
     */
    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    /**
     * Removes an item from the adapter.
     * Notifies that item has been removed.
     *
     * @param item to be removed
     */
    public void remove(T item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * Returns whether adapter is empty or not.
     *
     * @return `true` if adapter is empty or `false` otherwise
     */
    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    /**
     * Indicates whether each item in the data set can be represented with a unique identifier
     * of type {@link Long}.
     *
     * @param hasStableIds Whether items in data set have unique identifiers or not.
     * @see #hasStableIds()
     * @see #getItemId(int)
     */
    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }


    /**
     * Set click listener, which must extend {@link BaseRecyclerViewListener}
     *
     * @param listener click listener
     */
    public void setListener(RVListener listener) {
        this.listener = listener;
    }

    /**
     * Inflates a view.
     *
     * @param layout       layout to me inflater
     * @param parent       container where to inflate
     * @param attachToRoot whether to attach to root or not
     * @return inflated View
     */
    @SuppressWarnings("WeakerAccess")
    @NonNull
    protected View inflate(@LayoutRes final int layout, @Nullable final ViewGroup parent, @SuppressWarnings("SameParameterValue") final boolean attachToRoot) {
        return layoutInflater.inflate(layout, parent, attachToRoot);
    }

    /**
     * Inflates a view.
     *
     * @param layout layout to me inflater
     * @param parent container where to inflate
     * @return inflated View
     */
    @NonNull
    protected View inflate(@LayoutRes final int layout, final @Nullable ViewGroup parent) {
        return inflate(layout, parent, false);
    }

    public interface OnRecyclerObjectClickViewListener<T> extends BaseRecyclerViewListener {
        /**
         * Item has been clicked.
         *
         * @param item object associated with the clicked item.
         */
        void onItemClicked(T item);
    }
}

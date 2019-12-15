package com.ams.androiddevkit.baseClasses.recyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class RecyclerArrayAdapter<T, VH: RecyclerView.ViewHolder?> @JvmOverloads constructor(items: List<T> = ArrayList()) : RecyclerView.Adapter<VH>() {
    /**
     * Contains the list of objects that represent the data.
     */
    protected var mItems: MutableList<T>
    protected var diffUtilCallback: BaseDiffUtilCallback<T>? = null
    protected var recyclerView: RecyclerView? = null

    constructor(items: Array<T>?): this(if (items != null) ArrayList<T>(Arrays.asList(*items)) else ArrayList<T>()) {}

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
    abstract override fun onBindViewHolder(holder: VH, position: Int)
    override fun getItemCount(): Int {
        return mItems.size
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     * data set.
     * @return The data at the specified position.
     */
    fun getItem(position: Int): T {
        return mItems[position]
    }

    val items: List<T>
        get() = mItems

    /**
     * Updates specified object at index of the adapter.
     *
     * @param object the object to update.
     */
    fun update(index: Int, `object`: T?) {
        if (null != `object`) {
            mItems[index] = `object`
            notifyItemChanged(index)
        }
    }

    /**
     * Adds the specified object at the end of this adapter.
     *
     * @param object the object to add.
     */
    fun add(`object`: T?) {
        if (null != `object`) {
            mItems.add(`object`)
            notifyItemInserted(mItems.size - 1)
        }
    }

    /**
     * Adds the specified object at the end of this adapter.
     *
     * @param object the object to add.
     */
    fun addFront(`object`: T?) {
        if (null != `object`) {
            mItems.add(0, `object`)
            notifyItemInserted(0)
        }
    }

    /**
     * Adds the objects in the specified collection to the end of this adapter. The
     * objects are added in the order in which they are returned from the
     * collection's iterator.
     *
     * @param items the collection of objects.
     */
    fun addAll(items: List<T>) {
        if (items.isNotEmpty()) {
            val prevItemCount = mItems.size
            mItems.addAll(items)
            val newItemCount = mItems.size
            notifyItemRangeInserted(prevItemCount, newItemCount - prevItemCount)
        }
    }

    fun addAllBefore(collection: Collection<T>?) {
        if (null != collection && !collection.isEmpty()) {
            mItems.addAll(collection)
            val newItemCount = mItems.size
            notifyItemRangeInserted(0, newItemCount)
        }
    }

    fun insert(position: Int, `object`: T) {
        mItems.add(position, `object`)
        notifyItemInserted(position)
    }

    fun remove(position: Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * Removes all elements from this adapter, leaving it empty.
     */
    fun clear() {
        val itemCount = mItems.size
        mItems.clear()
        notifyItemRangeRemoved(0, itemCount)
    }

    fun attachDiffUtil(diffUtilCallback: BaseDiffUtilCallback<T>?, recyclerView: RecyclerView?) {
        this.diffUtilCallback = diffUtilCallback
        this.recyclerView = recyclerView
    }

    fun swapItems(newItems: MutableList<T>?) {
        if (diffUtilCallback == null) swapItemsWithoutDiffUtil(newItems) else swapItemsWithDiffUtil(newItems)
    }

    /**
     * @param newItems
     */
    fun swapItemsWithDiffUtil(newItems: List<T>?) {
        val recyclerViewState = recyclerView!!.layoutManager!!.onSaveInstanceState()
        diffUtilCallback!!.setLists(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback!!)
        mItems.clear()
        mItems.addAll(newItems!!)
        diffResult.dispatchUpdatesTo(this)
        recyclerView!!.layoutManager!!.onRestoreInstanceState(recyclerViewState)
    }

    fun swapItemsWithoutDiffUtil(newItems: MutableList<T>?) {
        if (mItems == newItems) {
            return
        }
        if (newItems != null) {
            mItems = newItems
            notifyDataSetChanged()
        } else {
            mItems = ArrayList()
            notifyDataSetChanged()
        }
    }

    init {
        mItems = items.toMutableList()
    }
}
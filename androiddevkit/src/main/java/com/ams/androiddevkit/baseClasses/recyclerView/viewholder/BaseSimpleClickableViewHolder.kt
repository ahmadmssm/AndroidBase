package com.ams.androiddevkit.baseClasses.recyclerView.viewholder

import androidx.annotation.LayoutRes
import android.view.ViewGroup

abstract class BaseSimpleClickableViewHolder<T>(parent: ViewGroup, @LayoutRes resLayout: Int, @Suppress("MemberVisibilityCanBePrivate") protected var mItemClickListener: ItemClickListener): BaseSimpleViewHolder<T>(parent, resLayout) {

    protected fun invokeListener() {
        mItemClickListener.onItemClicked(adapterPosition)
    }

    interface ItemClickListener {
        fun onItemClicked(position: Int)
    }
}

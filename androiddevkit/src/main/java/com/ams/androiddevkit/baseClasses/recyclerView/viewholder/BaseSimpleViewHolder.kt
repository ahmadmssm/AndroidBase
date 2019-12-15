package com.ams.androiddevkit.baseClasses.recyclerView.viewholder

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseSimpleViewHolder<T>(parent: ViewGroup, @LayoutRes resLayout: Int): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(resLayout, parent, false)) {

    protected val context: Context get() = itemView.context

    abstract fun bind(item: T, index: Int)
}

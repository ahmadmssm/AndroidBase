package com.ams.androiddevkit.baseClasses.recyclerView

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T, VH: RecyclerView.ViewHolder>(items: List<T> = listOf()): RecyclerArrayAdapter<T, VH>(items)

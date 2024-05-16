package com.jeckso.reddit.android.adapter

fun interface OnItemClickListener<T> {

    fun onItemClick(item: T, position: Int)

}
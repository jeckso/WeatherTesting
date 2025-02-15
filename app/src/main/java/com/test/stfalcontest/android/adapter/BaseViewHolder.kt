package com.test.stfalcontest.android.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jeckso.reddit.android.adapter.OnItemClickListener

abstract class BaseViewHolder<T, VB : ViewBinding>(protected val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {

    open fun bind(item: T, listener: OnItemClickListener<T>?) {
        itemView.setOnClickListener { listener?.onItemClick(item, bindingAdapterPosition) }
    }

    open fun unbind() {
        itemView.setOnClickListener(null)
    }
}
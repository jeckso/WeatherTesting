package com.test.stfalcontest.view.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jeckso.reddit.android.adapter.OnItemClickListener
import com.test.stfalcontest.android.adapter.BaseRecyclerViewAdapter
import com.test.stfalcontest.databinding.LiDetailsBinding
import com.test.stfalcontest.view.details.DetailVM

class DetailsAdapter(
    listener: OnItemClickListener<DetailVM>
) : BaseRecyclerViewAdapter<DetailVM, DetailVH>(listener) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailVH {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = LiDetailsBinding.inflate(inflater, parent, false)
        return DetailVH(viewBinding)
    }

}
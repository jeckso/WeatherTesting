package com.test.stfalcontest.view.details.adapter

import com.jeckso.reddit.android.adapter.OnItemClickListener
import com.test.stfalcontest.android.adapter.BaseViewHolder
import com.test.stfalcontest.databinding.LiDetailsBinding
import com.test.stfalcontest.view.details.DetailVM

class DetailVH(binding: LiDetailsBinding) : BaseViewHolder<DetailVM, LiDetailsBinding>(binding) {

    override fun bind(item: DetailVM, listener: OnItemClickListener<DetailVM>?) {
        super.bind(item, listener)
        with(binding) {
            setup(item)
        }
    }

    private fun LiDetailsBinding.setup(item: DetailVM) {
        name.text = item.name
        value.text = item.value

    }

}
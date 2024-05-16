package com.test.stfalcontest.view.list.adapter

import com.bumptech.glide.Glide
import com.jeckso.reddit.android.adapter.OnItemClickListener
import com.test.stfalcontest.R
import com.test.stfalcontest.android.adapter.BaseViewHolder
import com.test.stfalcontest.databinding.LiCityBinding
import com.test.stfalcontest.domain.model.Weather

class CityVH(binding: LiCityBinding) : BaseViewHolder<Weather, LiCityBinding>(binding) {

    override fun bind(item: Weather, listener: OnItemClickListener<Weather>?) {
        super.bind(item, listener)
        with(binding) {
            setup(item)
        }
    }

    private fun LiCityBinding.setup(item: Weather) {
        name.text = item.name
        value.text = value.context.getString(R.string.placeholder_temp, item.temp.toString())

    }

}
package com.test.stfalcontest.view.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jeckso.reddit.android.adapter.OnItemClickListener
import com.test.stfalcontest.android.adapter.BaseRecyclerViewAdapter
import com.test.stfalcontest.databinding.LiCityBinding
import com.test.stfalcontest.domain.model.Weather

class CityAdapter(
    listener: OnItemClickListener<Weather>
) : BaseRecyclerViewAdapter<Weather, CityVH>(listener) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityVH {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = LiCityBinding.inflate(inflater, parent, false)
        return CityVH(viewBinding)
    }

}
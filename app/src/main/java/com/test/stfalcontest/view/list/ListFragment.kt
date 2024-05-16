package com.test.stfalcontest.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeckso.reddit.android.adapter.OnItemClickListener
import com.test.stfalcontest.R
import com.test.stfalcontest.databinding.FragmentListBinding
import com.test.stfalcontest.domain.model.Weather
import com.test.stfalcontest.view.base.BaseFragment
import com.test.stfalcontest.view.details.DetailVM
import com.test.stfalcontest.view.list.adapter.CityAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment constructor() : BaseFragment<ListViewModel, FragmentListBinding>(),
    OnItemClickListener<Weather> {

    override val viewModel: ListViewModel by viewModels()

    private val postAdapter = CityAdapter(this)


    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentListBinding {
        return FragmentListBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch() {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.weatherFlow.collect {
                    postAdapter.items = it
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.setupRV()
    }

    override fun onItemClick(item: Weather, position: Int) {
        val details = mapWeatherToDetailVM(item).toTypedArray()
        findNavController().navigate(R.id.action_listFragment_to_detailsFragment, bundleOf("detailsList" to details))

    }

    private fun FragmentListBinding.setupRV() {
        with(postsRV) {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun mapWeatherToDetailVM(weather: Weather): List<DetailVM> {
        val detailList = mutableListOf<DetailVM>()
        detailList.add(DetailVM("Name", weather.name))
        detailList.add(DetailVM("Temperature", weather.temp.toString()))
        detailList.add(DetailVM("Humidity", weather.humidity.toString()))
        detailList.add(DetailVM("Feels Like", weather.feelsLike.toString()))
        detailList.add(DetailVM("Group", weather.group))

        return detailList
    }

}
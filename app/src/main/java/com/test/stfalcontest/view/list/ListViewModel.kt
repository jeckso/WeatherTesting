package com.test.stfalcontest.view.list

import androidx.lifecycle.viewModelScope
import com.test.stfalcontest.domain.WeatherRepositoryImpl
import com.test.stfalcontest.domain.model.City
import com.test.stfalcontest.domain.model.Weather
import com.test.stfalcontest.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repo: WeatherRepositoryImpl
) : BaseViewModel() {

    companion object {
        private val citiesList = listOf(
            City("52.520008", "13.404954", "Berlin"),
            City("48.864716", "2.349014", "Paris"),
            City("35.652832", "139.839478", "Tokyo"),
            City("40.416775", "-3.703790", "Madrid"),
            City("50.450001", "30.523333", "Kyiv"),

            )
    }

    val weatherFlow: SharedFlow<List<Weather>> = flow {
        val results = mutableListOf<Weather>()
        citiesList.map {
            results.add(repo.getWeather(it))
        }
        emit(results.toList())
    }.shareIn(viewModelScope, started = SharingStarted.WhileSubscribed(), replay = 1)


}
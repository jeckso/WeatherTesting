package com.test.stfalcontest

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
class MainViewModel @Inject constructor(
    private val repo: WeatherRepositoryImpl
) : BaseViewModel() {


}
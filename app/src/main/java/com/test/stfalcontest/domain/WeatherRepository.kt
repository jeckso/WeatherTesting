package com.test.stfalcontest.domain

import com.test.stfalcontest.domain.model.City
import com.test.stfalcontest.domain.model.Weather


interface WeatherRepository{
    suspend fun getWeather(city: City): Weather

    suspend fun getListWeathers():List<Weather>
}
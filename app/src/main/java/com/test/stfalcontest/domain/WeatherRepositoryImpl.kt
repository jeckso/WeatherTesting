package com.test.stfalcontest.domain

import com.test.stfalcontest.BuildConfig
import com.test.stfalcontest.data.remote.WeatherService
import com.test.stfalcontest.domain.model.City
import com.test.stfalcontest.domain.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val service: WeatherService) : WeatherRepository {

    override suspend fun getWeather(city: City): Weather {
        return withContext(Dispatchers.IO) {
            val response = service.getWeather(city.lat, city.lon, "metric", BuildConfig.API_KEY)
            with(response) {
                Weather(name, main.temp, main.humidity, main.feelsLike, weather[0].main)
            }
        }
    }

    override suspend fun getListWeathers(): List<Weather> {
        TODO("Not yet implemented")
    }

}
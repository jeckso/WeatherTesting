package com.test.stfalcontest.data.remote

import com.test.stfalcontest.data.remote.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    companion object {
        private const val ENDPOINT_GET_WEATHER = "weather"
    }

    @GET(ENDPOINT_GET_WEATHER)
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): WeatherResponse
}
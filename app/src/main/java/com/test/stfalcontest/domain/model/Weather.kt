package com.test.stfalcontest.domain.model

data class Weather(
    val name: String,
    val temp: Double,
    val humidity: Int,
    val feelsLike: Double,
    val group: String
)

package com.rh.simpleweatherapp.presentation

import com.rh.simpleweatherapp.domain.model.WeatherModel

data class WeatherState(
    val weather : WeatherModel? = null,
    val isLoading : Boolean = false,
    val error : String? = null
)

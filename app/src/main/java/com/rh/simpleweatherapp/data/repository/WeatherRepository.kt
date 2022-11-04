package com.rh.simpleweatherapp.data.repository

import com.rh.simpleweatherapp.domain.model.WeatherModel
import com.rh.simpleweatherapp.domain.util.Resource

interface WeatherRepository {
    suspend fun getWeather(lat:Double,lon:Double) : Resource<WeatherModel>
}
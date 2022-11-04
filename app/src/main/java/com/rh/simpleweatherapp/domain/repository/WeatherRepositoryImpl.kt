package com.rh.simpleweatherapp.domain.repository

import com.rh.simpleweatherapp.data.WeatherApi
import com.rh.simpleweatherapp.data.remote.dto.toWeatherModel
import com.rh.simpleweatherapp.data.repository.WeatherRepository
import com.rh.simpleweatherapp.domain.model.WeatherModel
import com.rh.simpleweatherapp.domain.util.Resource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val api:WeatherApi) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): Resource<WeatherModel> {
        return try {
            Resource.Success(data = api.getWeather(lat,lon).toWeatherModel())
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}
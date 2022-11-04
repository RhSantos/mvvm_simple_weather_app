package com.rh.simpleweatherapp.data

import com.rh.simpleweatherapp.data.remote.dto.WeatherDto
import com.rh.simpleweatherapp.domain.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather?")
    suspend fun getWeather(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("units") units:String = "metric",
        @Query("appid") appid:String = Constants.API_KEY
    ) : WeatherDto
}
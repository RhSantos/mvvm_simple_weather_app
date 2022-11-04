package com.rh.simpleweatherapp.domain.model

data class WeatherModel(
    val local:String,
    val icon:String,
    val temp:Double,
    val feels_like:Double,
    val description:String
)

package com.rh.simpleweatherapp.data.remote.dto

import com.rh.simpleweatherapp.domain.model.WeatherModel

data class WeatherDto(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

fun WeatherDto.toWeatherModel() : WeatherModel{
    return WeatherModel(name,weather[0].icon,main.temp,main.feels_like,weather[0].main)
}
package com.rh.simpleweatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rh.simpleweatherapp.data.repository.WeatherRepository
import com.rh.simpleweatherapp.domain.location.LocationTracker
import com.rh.simpleweatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeather(){
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when(val result = repository.getWeather(location.data?.latitude ?: return@let,location.data?.longitude ?: return@let)){
                    is Resource.Success -> {
                        state = state.copy(
                            weather = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weather = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        } ?: kotlin.run {
            state = state.copy(
                isLoading = false,
                error = "Couldn't retrieve location. Make sure to grant permission and enable your GPS."
            )
        }
    }
}
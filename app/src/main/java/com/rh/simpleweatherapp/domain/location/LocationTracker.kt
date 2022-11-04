package com.rh.simpleweatherapp.domain.location

import android.location.Location
import com.rh.simpleweatherapp.domain.util.Resource

interface LocationTracker {
    suspend fun getCurrentLocation() : Resource<Location?>
}
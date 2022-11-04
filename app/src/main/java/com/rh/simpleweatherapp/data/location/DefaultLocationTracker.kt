package com.rh.simpleweatherapp.data.location

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.rh.simpleweatherapp.domain.location.LocationTracker
import com.rh.simpleweatherapp.domain.util.Resource
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.jar.Manifest
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultLocationTracker @Inject constructor(
    private val locationClient : FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {

    lateinit var locationRequest:LocationRequest

    override suspend fun getCurrentLocation(): Resource<Location?> {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return when (true){
            !hasAccessCoarseLocationPermission -> Resource.Error("Enable Coarse Location Permission on Android Manifest")
            !hasAccessFineLocationPermission -> Resource.Error("Enable Fine Location Permission on Android Manifest")
            !isGpsEnabled -> Resource.Error("Check if you Gps is Enabled")
            else -> suspendCancellableCoroutine { cont ->
                locationClient.lastLocation.apply {
                    if(isComplete){
                        if(isSuccessful){
                            cont.resume(Resource.Success(result))
                        } else {
                            cont.resume(Resource.Error("Not successful trying to pick last location"))
                        }
                        return@suspendCancellableCoroutine
                    }
                    addOnSuccessListener {
                        cont.resume(Resource.Success(it))
                    }
                    addOnFailureListener{
                        cont.resume(Resource.Error("Error Getting Last Location from Location Client"))
                    }
                    addOnCanceledListener {
                        cont.cancel()
                    }
                }
            }
        }
    }
}
package com.rh.simpleweatherapp.presentation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import com.rh.simpleweatherapp.presentation.theme.DayColor
import com.rh.simpleweatherapp.presentation.theme.NightColor
import com.rh.simpleweatherapp.presentation.theme.SimpleWeatherAppTheme
import com.rh.simpleweatherapp.presentation.ui.screen.components.SimpleWeatherScreen
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.jar.Manifest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionsLauncher : ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            viewModel.loadWeather()
        }
        permissionsLauncher.launch(arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        val calendar = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        if(calendar in 6..17) this.window.navigationBarColor = DayColor.toArgb()
        else this.window.navigationBarColor = NightColor.toArgb()

        setContent {
            SimpleWeatherAppTheme {
                Surface(
                    Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.secondary
                ) {
                    if(viewModel.state.error?.isNotBlank() == true)
                        Text(text = viewModel.state.error!!, textAlign = TextAlign.Center)
                    SimpleWeatherScreen(state = viewModel.state)
                }

            }
        }
    }
}

@Composable
fun test(){

    Surface(Modifier.fillMaxSize()) {
        Text(
            textAlign = TextAlign.Center,
            text = ""
        )
    }
}
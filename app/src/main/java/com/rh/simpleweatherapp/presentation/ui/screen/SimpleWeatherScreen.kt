package com.rh.simpleweatherapp.presentation.ui.screen.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rh.simpleweatherapp.R
import com.rh.simpleweatherapp.data.remote.dto.Main
import com.rh.simpleweatherapp.presentation.WeatherState
import com.rh.simpleweatherapp.presentation.WeatherViewModel
import com.rh.simpleweatherapp.presentation.theme.*
import java.util.*

@Composable
fun SimpleWeatherScreen(
    state: WeatherState
){

    state.weather?.let {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.secondary
        ) {
            val calendar = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val colorPrincipal =
                if(calendar in 6..17) DayColor
                else NightColor
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset((-10).dp, (-50).dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "Location"
                    )
                    Text(
                        text = state.weather.local,
                        color = TextColor,
                        letterSpacing = (-1).sp,
                        fontSize = 30.sp,
                        fontFamily = FontFamily(Font(R.font.inter_regular)))
                }

                Image(
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                        .offset(0.dp, (-40).dp),
                    painter = WeatherIcon(state),
                    contentDescription = "Weather_Icon")

                CardWeather(
                    state = state,
                    colorPrincipal = colorPrincipal
                )
            }
        }
    }

}

@Composable
fun CardWeather(
    state: WeatherState,
    colorPrincipal:Color
){
    state.weather?.let {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(390.dp)
        ) {
            Card(
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(50.dp, 50.dp)),
                colors = CardDefaults.cardColors(containerColor = colorPrincipal),
            ) {}

            Card(
                shape = RectangleShape,
                modifier = Modifier
                    .width(350.dp)
                    .height(350.dp)
                    .align(Alignment.TopCenter)
                    .clip(RoundedCornerShape(30.dp)),
                colors = CardDefaults.cardColors(containerColor = MainColor),
            )
            {

                Row(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(20.dp,0.dp)
                ) {
                    Text(
                        text = "${it.temp.toInt()}",
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        fontSize = 150.sp,
                        letterSpacing = (-6).sp,
                        color = TextColor
                    )
                    Text(
                        text = "º",
                        modifier = Modifier.offset((-5).dp,0.dp),
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        fontSize = 100.sp,
                        color = TextColor
                    )
                }


                Text(
                    text = it.description.toString(),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(0.dp, (-10).dp),
                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                    fontSize = 35.sp,
                    letterSpacing = (-1).sp,
                    color = TextColor
                )

                Text(
                    text ="Feels Like Temperature ${it.feels_like.toInt()}º",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(0.dp, 20.dp),
                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                    fontSize = 20.sp,
                    letterSpacing = (-1).sp,
                    color = TextColor
                )

            }
        }
    }

}
@Composable
fun WeatherIcon(state:WeatherState) : Painter {
    state.weather?.let {
        return when(it.icon){
            "01d" -> { painterResource(id = R.drawable.img_01d) }
            "01n" -> { painterResource(id = R.drawable.img_01n) }
            "02d" -> { painterResource(id = R.drawable.img_02d) }
            "02n" -> { painterResource(id = R.drawable.img_02n) }
            "03d" -> { painterResource(id = R.drawable.img_03d) }
            "03n" -> { painterResource(id = R.drawable.img_03n) }
            "04d" -> { painterResource(id = R.drawable.img_04d) }
            "04n" -> { painterResource(id = R.drawable.img_04n) }
            "09d" -> { painterResource(id = R.drawable.img_09d) }
            "09n" -> { painterResource(id = R.drawable.img_09n) }
            "010d" -> { painterResource(id = R.drawable.img_10d) }
            "010n" -> { painterResource(id = R.drawable.img_10n) }
            "011d" -> { painterResource(id = R.drawable.img_11d) }
            "011n" -> { painterResource(id = R.drawable.img_11n) }
            "013d" -> { painterResource(id = R.drawable.img_13d) }
            "013n" -> { painterResource(id = R.drawable.img_13n) }
            "050d" -> { painterResource(id = R.drawable.img_50d) }
            "050n" -> { painterResource(id = R.drawable.img_50n) }
            else -> {painterResource(id = R.drawable.img_01d)}
        }
    }
    return painterResource(id = R.drawable.img_01d)
}
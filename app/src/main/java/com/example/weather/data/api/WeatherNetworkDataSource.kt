package com.example.weather.data.api

import androidx.lifecycle.LiveData
import com.example.weather.data.api.responce.CurrentWeatherInfo

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<CurrentWeatherInfo>

    suspend fun fethCurrentWeather(
        location: String,
        languageCode: String
    )

}
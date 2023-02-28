package com.example.weather.data.model

import com.example.weather.data.db.entity.Current
import com.example.weather.data.db.entity.Location


data class CurrentWeatherInfo(
    val current: Current,
    val location: Location
)
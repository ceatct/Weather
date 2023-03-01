package com.example.weather.data.current

import androidx.lifecycle.ViewModel
import com.example.weather.data.internal.UnitSystem
import com.example.weather.data.internal.lazyDeferred
import com.example.weather.data.repository.ForecastRepository

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val unitSystem = UnitSystem.METRIC

    val isMetric: Boolean
    get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }

}
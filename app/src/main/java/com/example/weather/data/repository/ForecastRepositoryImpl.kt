package com.example.weather.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.weather.data.api.WeatherNetworkDataSource
import com.example.weather.data.api.responce.CurrentWeatherInfo
import com.example.weather.data.db.dao.CurrentWeatherDao
import com.example.weather.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            presistFethedCurrentWeather(newCurrentWeather)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if(metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }
    }

    private fun presistFethedCurrentWeather(fethedWeather : CurrentWeatherInfo){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fethedWeather.current)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initWeatherData(){
        if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fethCurrentWeather()
    }

    private suspend fun fethCurrentWeather(){
        weatherNetworkDataSource.fethCurrentWeather(
            "Wellington",
            Locale.getDefault().language
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchCurrentNeeded(lastFethTime:ZonedDateTime) : Boolean{
        val thirtuMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFethTime.isBefore(thirtuMinutesAgo)
    }
}
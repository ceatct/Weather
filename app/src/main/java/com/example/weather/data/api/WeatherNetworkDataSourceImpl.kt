package com.example.weather.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather.data.api.responce.CurrentWeatherInfo
import com.example.weather.data.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val apiWeatherServise: ApiWeatherServise
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherInfo>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherInfo>
        get() = _downloadedCurrentWeather

    override suspend fun fethCurrentWeather(location: String, languageCode: String) {
        try{
            val fethedCurrentWeather = apiWeatherServise
                .getCurrentWeather(location, languageCode)
                .await()

            _downloadedCurrentWeather.postValue(fethedCurrentWeather)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity", "NoInternetConnection", e)
        }
    }
}
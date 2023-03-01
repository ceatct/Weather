package com.example.weather.data

import android.app.Application
import com.example.weather.data.api.ApiWeatherServise
import com.example.weather.data.api.WeatherNetworkDataSource
import com.example.weather.data.api.WeatherNetworkDataSourceImpl
import com.example.weather.data.api.responce.ConnectivityInterceptor
import com.example.weather.data.api.responce.ConnectivityInterceptorImpl
import com.example.weather.data.current.CurrentWeatherViewModelFactory
import com.example.weather.data.db.ForecastDatabase
import com.example.weather.data.repository.ForecastRepository
import com.example.weather.data.repository.ForecastRepositoryImpl
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiWeatherServise(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}
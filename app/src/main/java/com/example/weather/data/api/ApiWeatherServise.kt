package com.example.weather.data.api

import com.example.weather.data.api.responce.ConnectivityInterceptor
import com.example.weather.data.api.responce.ConnectivityInterceptorImpl
import com.example.weather.data.api.responce.CurrentWeatherInfo
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//http://api.weatherapi.com/v1/current.json?key=a8a08f2cc90a472eb6990608232602&q=Kiev&aqi=no

const val API_KEY = "a8a08f2cc90a472eb6990608232602"

interface ApiWeatherServise {

    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") location : String,
        @Query("lang") languageCode : String = "en"
    ):Deferred<CurrentWeatherInfo>

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApiWeatherServise{
            val requestInterceptor = Interceptor{ chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.weatherapi.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiWeatherServise::class.java)
        }
    }

}
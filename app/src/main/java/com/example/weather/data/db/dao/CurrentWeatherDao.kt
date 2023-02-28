package com.example.weather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather.data.db.entity.CURRENT_WEATHER_ID
import com.example.weather.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.example.weather.data.db.unitlocalized.MetricCurentWeatherEntry
import com.example.weather.data.model.CurrentWeatherInfo

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherInfo: CurrentWeatherInfo)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurentWeatherEntry>

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>

}
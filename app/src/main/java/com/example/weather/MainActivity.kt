package com.example.weather

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.weather.data.api.ApiWeatherServise
import com.example.weather.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var apiServise: ApiWeatherServise

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiServise = ApiWeatherServise()

        GlobalScope.launch (Dispatchers.Main) {
            val currentWeatherInfo = apiServise.getCurrentWeather("London").await()
            binding.textView.text = currentWeatherInfo.current.tempC.toString()
        }

    }
}
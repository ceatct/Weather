package com.example.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.data.current.CurrentFragment
import com.example.weather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(
            R.id.container, CurrentFragment()).commit()

    }

}
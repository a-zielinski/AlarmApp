package com.azapps.momapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.azapps.momapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }
}
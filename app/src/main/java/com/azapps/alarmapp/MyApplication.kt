package com.azapps.alarmapp

import com.azapps.alarmapp.di.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class MyApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    private val applicationInjector = DaggerAppComponent.builder().application(this).build()

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector
}
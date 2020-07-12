package com.azapps.momapp

import android.app.Application
import com.azapps.momapp.ui.alarm.AlarmFragment
import com.azapps.momapp.ui.alarm.AlarmViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DependencyInjectionModule::class])
internal interface AppComponent {
    //fun provideAlarmViewModelFactory(): AlarmViewModelFactory
    fun inject(main: AlarmFragment?)
}

class MomAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}
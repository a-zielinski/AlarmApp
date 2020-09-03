package com.azapps.alarmapp.di.module

import com.azapps.alarmapp.data.receivers.AlarmBroadcastReceiver
import com.azapps.alarmapp.data.services.AlarmService
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ServiceModule {
    @ContributesAndroidInjector
    abstract fun contributeAlarmService(): AlarmService
    @ContributesAndroidInjector
    abstract fun contributeAlarmBroadcastReceiver(): AlarmBroadcastReceiver
}
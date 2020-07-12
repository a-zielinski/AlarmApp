package com.azapps.momapp

import com.azapps.momapp.ui.alarm.AlarmViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
internal class DependencyInjectionModule {
    @Provides
    @Singleton
    fun provideAlarmViewModelFactory(): AlarmViewModelFactory {
        return AlarmViewModelFactory()
    }
}
package com.azapps.alarmapp.di.module

import com.azapps.alarmapp.data.repository.AlarmRepository
import com.azapps.alarmapp.data.repository.AlarmRepositoryImpl


import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class RepositoryModule() {
    @Binds
    abstract fun bindAlarmRepository(repository: AlarmRepositoryImpl): AlarmRepository
}
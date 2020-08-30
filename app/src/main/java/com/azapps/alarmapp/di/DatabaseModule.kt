package com.azapps.alarmapp.di

import android.app.Application
import androidx.room.Room
import com.azapps.alarmapp.data.db.AppRoomDatabase
import com.azapps.alarmapp.data.db.daos.AlarmDao


import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Suppress("unused")
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesAlarmDao(app : Application): AlarmDao {
        val database = Room.databaseBuilder(app, AppRoomDatabase::class.java, "demo-db").build();
        return database.getAlarmDao();
    }
}
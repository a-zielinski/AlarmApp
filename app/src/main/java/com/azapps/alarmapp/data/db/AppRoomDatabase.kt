package com.azapps.alarmapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.azapps.alarmapp.data.db.daos.AlarmDao
import com.azapps.alarmapp.data.db.entities.Alarm

@TypeConverters(LocalDateConverter::class)
@Database(entities = [Alarm::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun getAlarmDao(): AlarmDao

}
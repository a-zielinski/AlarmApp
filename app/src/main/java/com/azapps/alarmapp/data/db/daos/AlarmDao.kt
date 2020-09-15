package com.azapps.alarmapp.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.azapps.alarmapp.data.db.entities.Alarm

@Dao
interface AlarmDao {

    @Query("SELECT * from alarm_table WHERE id = :id")
    fun getAlarmById(id : Int): Alarm?

    @Query("SELECT * from alarm_table ORDER BY startTime ASC")
    fun getAllAlarms(): LiveData<List<Alarm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarm: Alarm) : Long

    @Query("DELETE FROM alarm_table WHERE id = :id")
    suspend fun delete(id :Int)

}
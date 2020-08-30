package com.azapps.alarmapp.data.repository

import androidx.lifecycle.LiveData
import com.azapps.alarmapp.data.db.entities.Alarm

interface AlarmRepository {
    val getAlarmRinging : LiveData<Alarm>
    val getOnAlarmStopped : LiveData<Unit>
    suspend fun turnAlarmOn(alarm: Alarm)
    suspend fun turnAlarmOff(alarm: Alarm)
    suspend fun setAlarm(alarm: Alarm): Int
    suspend fun getAlarmById(alarmId: Int): Alarm?
    fun getAllAlarms(): LiveData<List<Alarm>>
}
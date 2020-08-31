package com.azapps.alarmapp.data.providers

import androidx.lifecycle.LiveData
import com.azapps.alarmapp.data.db.entities.Alarm

interface AlarmDataProvider {
    fun setAlarm(alarm : Alarm) : Alarm
    fun getAlarmById(alarmId : Int) : Alarm?
    fun deleteAlarm(alarm: Alarm)
    fun getAllAlarms(): LiveData<List<Alarm>>
}
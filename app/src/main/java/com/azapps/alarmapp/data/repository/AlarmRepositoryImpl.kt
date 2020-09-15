package com.azapps.alarmapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.azapps.alarmapp.data.db.daos.AlarmDao
import com.azapps.alarmapp.data.db.entities.Alarm
import com.azapps.alarmapp.utils.LiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepositoryImpl @Inject constructor(private val alarmDao: AlarmDao) : AlarmRepository {
    private val alarmRinging = MutableLiveData<Alarm?>(null)

    override val getAlarmRinging: LiveData<Alarm?> = alarmRinging

    private val alarmStopped = LiveEvent<Unit>()
    override val getOnAlarmStopped: LiveData<Unit> = alarmStopped

    override suspend fun turnAlarmOn(alarm: Alarm) {
            alarmRinging.postValue(alarm)
    }

    override suspend fun getAlarmById(alarmId: Int): Alarm? {
        return alarmDao.getAlarmById(alarmId)
    }

    override fun getAllAlarms(): LiveData<List<Alarm>> {
        return alarmDao.getAllAlarms()

    }

    override suspend fun delete(alarm: Alarm) {
        if (alarm.id > 0)
            alarmDao.delete(alarm.id)
    }

    override fun turnAlarmOff(alarm: Alarm) {
        if (alarmRinging.value?.id == alarm.id) {
            alarmRinging.postValue(null)
            alarmStopped.postValue(Unit)
        }
    }

    override suspend fun setAlarm(alarm: Alarm): Int {
        return alarmDao.insert(alarm).toInt()
    }
}

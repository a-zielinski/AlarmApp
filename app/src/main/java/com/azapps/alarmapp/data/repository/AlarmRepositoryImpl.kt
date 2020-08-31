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
    private val alarmRinging = MutableLiveData<Alarm>(null)

    override val getAlarmRinging: LiveData<Alarm> = alarmRinging

    private val alarmStopped = LiveEvent<Unit>()
    override val getOnAlarmStopped: LiveData<Unit> = alarmStopped

    override suspend fun turnAlarmOn(alarm: Alarm) {
        CoroutineScope(Dispatchers.Main).launch {
            alarmRinging.value = alarm
        }
    }

    override suspend fun getAlarmById(alarmId: Int): Alarm? {
        return alarmDao.getAlarmById(alarmId)
    }

    override fun getAllAlarms(): LiveData<List<Alarm>> {
        return alarmDao.getAllAlarms()
    }

    override suspend fun turnAlarmOff(alarm: Alarm) {
        alarmDao.delete(alarm.id)

        if (alarmRinging.value?.id == alarm.id) {
            CoroutineScope(Dispatchers.Main).launch {
                alarmRinging.value = null
                alarmStopped.value = Unit
            }
        }
    }

    override suspend fun setAlarm(alarm: Alarm): Int {
        return alarmDao.insert(alarm).toInt()
    }
}

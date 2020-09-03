package com.azapps.alarmapp.ui.main.setalarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azapps.alarmapp.data.db.entities.Alarm
import com.azapps.alarmapp.data.repository.AlarmRepository
import com.azapps.alarmapp.di.module.IoDispatcher
import com.azapps.alarmapp.utils.LiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class SetAlarmViewModel @Inject constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val alarmRepository: AlarmRepository
) : ViewModel() {
    private val ioContext = viewModelScope.coroutineContext + ioDispatcher

    private val newToast = LiveEvent<String>()
    val observeNewToast: LiveData<String> = newToast

    private val startNewAlarmEvent = LiveEvent<Alarm>()
    val observeStartNewAlarmEvent: LiveData<Alarm> = startNewAlarmEvent

    fun getAllAlarms() : LiveData<List<Alarm>> =
     Transformations.map(alarmRepository.getAllAlarms()) { data -> data }


    fun turnAlarmOff(alarm: Alarm) {
        viewModelScope.launch(ioContext) {
            alarmRepository.turnAlarmOff(alarm)
        }
    }

    fun setAlarm(title: String, startTime: LocalDateTime) {
        val alarm = Alarm(
            startTime,
            title
        )

        viewModelScope.launch(ioContext) {
            alarm.id = alarmRepository.setAlarm(alarm)
            newToast.postValue("Alarm set to $startTime")
            startNewAlarmEvent.postValue(alarm)
        }
    }
}
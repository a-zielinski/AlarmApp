package com.azapps.alarmapp.ui.main.setalarm

import androidx.lifecycle.*
import com.azapps.alarmapp.data.db.entities.Alarm
import com.azapps.alarmapp.data.repository.AlarmRepository
import com.azapps.alarmapp.utils.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class SetAlarmViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository
) : ViewModel() {

    private val newToast = LiveEvent<String>()
    val observeNewToast: LiveData<String> = newToast

    private val startNewAlarmEvent = LiveEvent<Alarm>()
    val observeStartNewAlarmEvent: LiveData<Alarm> = startNewAlarmEvent

    val getAllAlarms: LiveData<List<Alarm>> =
        Transformations.map(alarmRepository.getAllAlarms()) { data -> data }


    fun turnAlarmOff(alarm: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            alarmRepository.turnAlarmOff(alarm)
        }
    }

    fun setAlarm(title: String, startTime : LocalDateTime) {
        val alarm = Alarm(
            startTime,
            title
        )

        viewModelScope.launch(Dispatchers.IO) {
            alarm.id = alarmRepository.setAlarm(alarm)
            launch(Dispatchers.Main) {
                newToast.value = "Alarm set to ${startTime.toString()}"
                startNewAlarmEvent.value = alarm
            }
        }

    }



}
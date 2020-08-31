package com.azapps.alarmapp.ui.main.alarmringing

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azapps.alarmapp.data.db.entities.Alarm
import com.azapps.alarmapp.data.repository.AlarmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmRingingViewModel @Inject constructor(private val alarmRepository: AlarmRepository) :
    ViewModel() {

    val getAlarmRinging: LiveData<Alarm> =
        Transformations.map(alarmRepository.getAlarmRinging) { data -> data }

    fun stopAlarm() {
        viewModelScope.launch(Dispatchers.IO) {
            alarmRepository.getAlarmRinging.value?.let {
                alarmRepository.turnAlarmOff(it)
            }
        }
    }
}
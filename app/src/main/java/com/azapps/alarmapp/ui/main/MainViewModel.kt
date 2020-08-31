package com.azapps.alarmapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.azapps.alarmapp.data.db.entities.Alarm
import com.azapps.alarmapp.data.repository.AlarmRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val alarmRepository: AlarmRepository) :
    ViewModel() {

    val getAlarmRinging: LiveData<Alarm?> =
        Transformations.map(alarmRepository.getAlarmRinging) { data -> data }

}

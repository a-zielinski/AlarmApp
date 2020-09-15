package com.azapps.alarmapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azapps.alarmapp.data.db.entities.Alarm
import com.azapps.alarmapp.data.repository.AlarmRepository
import com.azapps.alarmapp.di.module.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MainViewModel @Inject constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val alarmRepository: AlarmRepository) :
    ViewModel() {
    private val ioContext = viewModelScope.coroutineContext + ioDispatcher

    fun getAlarmRinging() =
        Transformations.map(alarmRepository.getAlarmRinging) { data -> data }

}

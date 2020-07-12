package com.azapps.momapp.ui.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AlarmViewModelFactory(
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlarmViewModel() as T
    }
}
package com.azapps.alarmapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.azapps.alarmapp.di.util.DaggerViewModelFactory
import com.azapps.alarmapp.di.util.ViewModelKey
import com.azapps.alarmapp.ui.main.MainViewModel
import com.azapps.alarmapp.ui.main.alarmringing.AlarmRingingViewModel
import com.azapps.alarmapp.ui.main.setalarm.SetAlarmViewModel


import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AlarmRingingViewModel::class)
    abstract fun bindAlarmRingingViewModel(alarmRingingViewModel: AlarmRingingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SetAlarmViewModel::class)
    abstract fun bindSetAlarmViewModel(setAlarmViewModel: SetAlarmViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel


}
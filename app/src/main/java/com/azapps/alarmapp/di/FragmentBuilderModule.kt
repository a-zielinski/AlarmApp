package com.azapps.alarmapp.di

import com.azapps.alarmapp.ui.main.alarmringing.AlarmRingingFragment
import com.azapps.alarmapp.ui.main.setalarm.SetAlarmFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeSetAlarmFragment(): SetAlarmFragment

    @ContributesAndroidInjector
    abstract fun contributeAlarmRingingFragment(): AlarmRingingFragment
}
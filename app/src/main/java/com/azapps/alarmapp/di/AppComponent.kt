package com.azapps.alarmapp.di

import android.app.Application
import com.azapps.alarmapp.MyApplication
import com.azapps.alarmapp.di.module.AppModule
import com.azapps.alarmapp.di.module.DispatcherModule
import com.azapps.alarmapp.di.module.MainActivityModule
import com.azapps.alarmapp.di.module.ServiceModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        MainActivityModule::class,
        ServiceModule::class,
        AppModule::class,
        DispatcherModule::class]
)
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
package com.azapps.alarmapp.di.module

import dagger.Module

@Module(
    includes = [
        ViewModelModule::class,
        RepositoryModule::class,
        DatabaseModule::class]
)
class AppModule {
}

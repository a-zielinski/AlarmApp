package com.azapps.alarmapp.di

import dagger.Module

@Module(
    includes = [
        ViewModelModule::class,
        RepositoryModule::class,
        DatabaseModule::class]
)
class AppModule {
}

package com.azapps.alarmapp.data.repository

import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.Exception
import kotlin.system.measureTimeMillis

internal class AlarmRepositoryImplTest {

    @Test
    fun setAlarm() = runBlocking {

        val job = launch() {
            println("JobStarted")
        }
    }
}
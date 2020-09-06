package com.azapps.alarmapp.ui.main.alarmringing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.azapps.alarmapp.data.db.entities.Alarm
import com.azapps.alarmapp.data.repository.AlarmRepository
import com.azapps.alarmapp.utils.BaseTestCase
import com.azapps.alarmapp.utils.InstantExecutorExtension
import com.azapps.alarmapp.utils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class AlarmRingingViewModelTest : BaseTestCase() {

    @BeforeEach
    fun initModel() {
        model = AlarmRingingViewModel(TestCoroutineDispatcher(), repository)
    }

    @Mock
    lateinit var repository: AlarmRepository
    private lateinit var model: AlarmRingingViewModel
    private val anyAlarm = Alarm(Companion.getLocalDateTimeNow(), "")

    @Test
    fun `should get alarm ringing when there is any`() = runBlockingTest {
        //arrange
        whenever(repository.getAlarmRinging).thenReturn(MutableLiveData(anyAlarm))

        // act
        val actualAlarm = model.getAlarmRinging().getOrAwaitValue()

        // assert
        verify(repository).getAlarmRinging
        assertEquals(anyAlarm, actualAlarm)
    }

    @Test
    fun `should get no alarm ringing when there isn't any`() = runBlockingTest {
        //arrange
        whenever(repository.getAlarmRinging).thenReturn(MutableLiveData(null))

        // act
        val actualAlarm = model.getAlarmRinging().getOrAwaitValue()

        // assert
        verify(repository).getAlarmRinging
        assertNull(actualAlarm)
    }

    @Test
    fun `should stop the alarm when there is any`() = runBlockingTest {
        //arrange
        whenever(repository.getAlarmRinging).thenReturn(MutableLiveData(anyAlarm))

        // act
        model.stopAlarm()

        // assert
        verify(repository).turnAlarmOff(anyAlarm)
    }
}
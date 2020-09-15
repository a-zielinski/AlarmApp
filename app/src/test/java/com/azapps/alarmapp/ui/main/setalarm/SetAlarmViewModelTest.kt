package com.azapps.alarmapp.ui.main.setalarm

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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class SetAlarmViewModelTest : BaseTestCase() {

    @BeforeEach
    fun initModel() {
        model = SetAlarmViewModel(TestCoroutineDispatcher(), repository)
    }

    @Mock
    lateinit var repository: AlarmRepository
    private lateinit var model: SetAlarmViewModel
    private val anyAlarm = Alarm(Companion.getLocalDateTimeNow(), "")

    @Test
    fun `turnAlarmOff turns alarm off`() = runBlockingTest {
        // arrange

        // act
        model.delete(anyAlarm)

        // assert
        verify(repository).delete(anyAlarm)
    }

    @Test
    fun `setAlarm sets alarm`() = runBlockingTest {
        // arrange
        val expectedId = 1
        whenever(repository.setAlarm(any())).thenReturn(expectedId)

        // act
        model.setAlarm(anyAlarm.title, anyAlarm.startTime)
        val actualAlarm = model.observeStartNewAlarmEvent.getOrAwaitValue()
        val actualToast = model.observeNewToast.getOrAwaitValue()

        // assert
        verify(repository).setAlarm(eq(anyAlarm))
        assertEquals("Alarm set to ${anyAlarm.startTime}", actualToast)
        assertEquals(expectedId, actualAlarm.id)
        assertEquals(anyAlarm.startTime, actualAlarm.startTime)
        assertEquals(anyAlarm.title, actualAlarm.title)
    }

    @Test
    fun `getAllAlarms returns no alarms`() = runBlockingTest {
        // arrange
        whenever(repository.getAllAlarms()).thenReturn(MutableLiveData(emptyList()))

        // act
        val actualAllAlarms = model.getAllAlarms().getOrAwaitValue()

        // assert
        assert(actualAllAlarms.isEmpty())

        return@runBlockingTest
    }

    @Test
    fun `getAllAlarms return alarms`() = runBlockingTest {
        // arrange
        whenever(repository.getAllAlarms()).thenReturn(MutableLiveData(listOf(anyAlarm, anyAlarm)))

        // act
        val actualAllAlarms = model.getAllAlarms().getOrAwaitValue()

        // assert
        val expectedCount = 2
        assert(actualAllAlarms.count() == expectedCount)

        return@runBlockingTest
    }
}
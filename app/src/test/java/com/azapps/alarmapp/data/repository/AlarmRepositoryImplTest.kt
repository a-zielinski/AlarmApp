package com.azapps.alarmapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.azapps.alarmapp.data.db.daos.AlarmDao
import com.azapps.alarmapp.data.db.entities.Alarm
import com.azapps.alarmapp.utils.BaseTestCase
import com.azapps.alarmapp.utils.InstantExecutorExtension
import com.azapps.alarmapp.utils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class AlarmRepositoryImplTest : BaseTestCase() {

    lateinit var repository: AlarmRepositoryImpl
    lateinit var anyAlarm: Alarm

    @Mock
    lateinit var alarmDao: AlarmDao

    @BeforeEach
    fun initSUT() {
        repository = AlarmRepositoryImpl(alarmDao)
        anyAlarm = Alarm(Companion.getLocalDateTimeNow(), "")
    }

    @Nested
    inner class GetAllAlarms() {
        @Test
        fun `should get alarms when there are some`() = runBlockingTest {
            //arrange
            whenever(alarmDao.getAllAlarms()).thenReturn(MutableLiveData(arrayListOf(anyAlarm)))

            // act
            val actualAlarmList = repository.getAllAlarms().getOrAwaitValue()

            // assert
            verify(alarmDao).getAllAlarms()
            assertNotNull(actualAlarmList)
            assert(actualAlarmList.isNotEmpty())
            assert(actualAlarmList.count() == 1)
            assertEquals(anyAlarm, actualAlarmList.first())
        }

        @Test
        fun `should get no alarm when there isn't any`() = runBlockingTest {
            //arrange
            whenever(alarmDao.getAllAlarms()).thenReturn(MutableLiveData(arrayListOf()))

            // act
            val actualAlarmList = repository.getAllAlarms().getOrAwaitValue()

            // assert
            verify(alarmDao).getAllAlarms()
            assertNotNull(actualAlarmList)
            assert(actualAlarmList.isEmpty())
        }
    }

    @Nested
    inner class GetAlarmById() {
        @Test
        fun `should get alarm when given valid id`() = runBlockingTest {
            //arrange

            val expectedId = 1
            anyAlarm.id = expectedId
            whenever(alarmDao.getAlarmById(expectedId)).thenReturn(anyAlarm)

            // act
            val actualAlarm = repository.getAlarmById(expectedId)

            // assert
            verify(alarmDao).getAlarmById(expectedId)
            assertNotNull(actualAlarm)
            assertEquals(anyAlarm, actualAlarm)
        }

        @Test
        fun `should get no alarm when given invalid id`() = runBlockingTest {
            //arrange

            val expectedId = 1
            whenever(alarmDao.getAlarmById(expectedId)).thenReturn(null)

            // act
            val actualAlarm = repository.getAlarmById(expectedId)

            // assert
            verify(alarmDao).getAlarmById(expectedId)
            assertNull(actualAlarm)
        }
    }

    @Nested
    inner class Delete() {
        @Test
        fun `should delete alarm when Alarm Id greater than 0`() = runBlockingTest {
            //arrange
            val validAlarm = anyAlarm.apply { id = 1 }
            // act
            repository.delete(validAlarm)

            // assert
            verify(alarmDao).delete(validAlarm.id)
        }

        @Test
        fun `should not delete alarm when Alarm Id less than 0`() = runBlockingTest {
            //arrange
            val invalidAlarm = anyAlarm.apply { id = -1 }

            // act
            repository.delete(invalidAlarm)

            // assert
            verify(alarmDao, never()).delete(any())
        }

        @Test
        fun `should not delete alarm when Alarm Id equal to 0`() = runBlockingTest {
            //arrange
            val invalidAlarm = anyAlarm.apply { id = 0 }

            // act
            repository.delete(invalidAlarm)

            // assert
            verify(alarmDao, never()).delete(any())
        }
    }

    @Nested
    inner class TurnOff() {
        @Test
        fun `should turn off the alarm when ringing`() = runBlockingTest {
            //arrange
            repository.turnAlarmOn(anyAlarm)
            var event: Unit? = null

            // act
            repository.turnAlarmOff(anyAlarm)

            // assert
            val alarmRinging = repository.getAlarmRinging.getOrAwaitValue()
            event = repository.getOnAlarmStopped.getOrAwaitValue()

            assertNull(alarmRinging)
            assertNotNull(event)
        }

        @Test
        fun `should not turn off the alarm when other alarm ringing`() = runBlockingTest {
            //arrange
            val otherAlarm = anyAlarm.copy().apply { id++ }
            repository.turnAlarmOn(otherAlarm)

            // act
            repository.turnAlarmOff(anyAlarm)

            // assert
            val actualAlarmRinging = repository.getAlarmRinging.getOrAwaitValue()

            assertEquals(otherAlarm, actualAlarmRinging)
        }

        @Test
        fun `should not turn off the alarm when not ringing`() = runBlockingTest {
            //arrange

            // act
            repository.turnAlarmOff(anyAlarm)

            // assert
            val actualAlarmRinging = repository.getAlarmRinging.getOrAwaitValue()

            assertNull(actualAlarmRinging)
        }
    }

    @Nested
    inner class SetAlarm() {

        @Test
        fun `should insert an alarm`() = runBlockingTest {
            //arrange

            whenever(alarmDao.insert(anyAlarm)).thenReturn(anyAlarm.id.toLong())

            // act
            val actualId = repository.setAlarm(anyAlarm)

            // assert
            verify(alarmDao).insert(anyAlarm)
            assertEquals(anyAlarm.id, actualId)
        }
    }
}
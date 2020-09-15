package com.azapps.alarmapp.utils

import org.junit.jupiter.api.BeforeEach
import org.mockito.MockitoAnnotations
import org.threeten.bp.LocalDateTime
import java.util.*


private fun Calendar.toLocalDateTime(): LocalDateTime = LocalDateTime.of(
    get(Calendar.YEAR),
    get(Calendar.MONTH) + 1,
    get(Calendar.DAY_OF_MONTH),
    get(Calendar.HOUR_OF_DAY),
    get(Calendar.MINUTE),
    get(Calendar.SECOND),
    get(Calendar.MILLISECOND) * 1000000
)


open class BaseTestCase {
    object Companion{
        /**an alternative of LocalDateTime.now(), as it requires initialization using AndroidThreeTen.init(context), which takes a bit time (loads a file)*/
        @JvmStatic
        fun getLocalDateTimeNow(): LocalDateTime = Calendar.getInstance().toLocalDateTime()
    }

    @BeforeEach
    fun initMocks() {
        MockitoAnnotations.openMocks(this)
    }
}

package com.azapps.alarmapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.azapps.alarmapp.data.db.LocalDateConverter
import org.threeten.bp.LocalDateTime

@TypeConverters(LocalDateConverter::class)
@Entity(tableName = "alarm_table")
data class Alarm(var startTime : LocalDateTime, var title : String)  {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
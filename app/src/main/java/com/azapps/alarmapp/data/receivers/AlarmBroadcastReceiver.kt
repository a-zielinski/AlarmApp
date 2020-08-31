package com.azapps.alarmapp.data.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.azapps.alarmapp.data.repository.AlarmRepository
import com.azapps.alarmapp.data.services.AlarmService
import com.azapps.alarmapp.internal.AppConstants
import dagger.android.AndroidInjection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

public class AlarmBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmRepository: AlarmRepository
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context)

        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            val toastText = String.format("Alarm Reboot")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            //startRescheduleAlarmsService(context)
        } else {
            val alarmId = intent.getIntExtra(AppConstants.INTENT_ALARM_ID, 0)
            ioScope.launch {
                alarmRepository.getAlarmById(alarmId)?.run {
                    alarmRepository.turnAlarmOn(this)
                    launch(Dispatchers.Main) {
                        startAlarmService(context, alarmId)
                    }
                }
            }
        }
    }

    private fun startAlarmService(context: Context, alarmId: Int) {
        val intentService = Intent(context, AlarmService::class.java)
        intentService.putExtra(
            AppConstants.INTENT_ALARM_ID,
            alarmId
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }

}
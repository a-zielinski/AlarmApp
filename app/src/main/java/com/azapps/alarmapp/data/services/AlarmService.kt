package com.azapps.alarmapp.data.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.azapps.alarmapp.R
import com.azapps.alarmapp.data.repository.AlarmRepository
import com.azapps.alarmapp.internal.AppConstants
import com.azapps.alarmapp.ui.main.MainActivity
import dagger.android.AndroidInjection
import javax.inject.Inject


class AlarmService : LifecycleService() {

    @Inject
    lateinit var alarmRepository: AlarmRepository

    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
        alarmRepository.getOnAlarmStopped
            .observe(this, Observer { onAlarmStopped() })

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer!!.isLooping = true
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    private fun onAlarmStopped() {
            this.stopSelf(1)
    }

    fun multiply(x: Double, y: Double) : Double {return x * y}

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val alarmTitle =
            String.format("%s Alarm", intent?.getStringExtra(AppConstants.INTENT_ALARM_ID))
        val notification =
            NotificationCompat.Builder(this, channelId)
                .setContentTitle(alarmTitle)
                .setContentText("Ring Ring .. Ring Ring")
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentIntent(getPendingIntent())
                .build()
        mediaPlayer!!.start()
        val pattern = longArrayOf(0, 100, 1000)
        vibrator!!.vibrate(pattern, 0)

        startForeground(1, notification)
        startAlarmActivity()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.stop()
        vibrator!!.cancel()
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }


    private fun startAlarmActivity() {
        applicationContext.startActivity(getIntent());
    }

    private fun getPendingIntent(): PendingIntent {
        return PendingIntent.getActivity(
            this, 0,
            getIntent(), PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun getIntent(): Intent {
        val intentActivity = Intent(applicationContext, MainActivity::class.java)
        intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intentActivity.action = Intent.ACTION_MAIN;
        intentActivity.addCategory(Intent.CATEGORY_LAUNCHER);
        return intentActivity
    }
}
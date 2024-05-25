package site.sayaz.ts3client.audio

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE
import android.os.Binder
import android.os.IBinder
import site.sayaz.ts3client.R
import site.sayaz.ts3client.main.MainActivity


class AudioService : Service() {
    private val binder = AudioBinder()
    val audioRecorder = AudioRecorder(this, AudioInput())
    val audioPlayer = AudioPlayer()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // create a notification
        val channelID = "MicrophoneService"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            channelID,
            "Microphone Service",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(notificationChannel)
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = Notification.Builder(this,channelID)
            .setContentTitle(R.string.connected_to_server.toString())
            .setContentText(R.string.microphone_service.toString())
            .setShowWhen(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setAutoCancel(false)
            .build()

        startForeground(FOREGROUND_SERVICE_TYPE_MICROPHONE, notification)

        return START_STICKY
    }
    override fun onBind(intent: Intent): IBinder {
        return (binder as AudioBinder)
    }


    inner class AudioBinder() : Binder() {

        val service: AudioService
            get() = this@AudioService
    }

}
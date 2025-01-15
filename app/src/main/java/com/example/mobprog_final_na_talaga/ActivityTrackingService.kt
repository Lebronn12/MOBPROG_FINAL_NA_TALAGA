package com.example.mobprog_final_na_talaga

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class ActivityTrackingService : Service() {

    companion object {
        const val CHANNEL_ID = "ActivityTrackingChannel"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(1, createNotification())

        startTracking() // Start your tracking logic

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Activity Tracking Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun createNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Activity Tracking")
            .setContentText("Tracking your activities...")
            .setSmallIcon(R.drawable.refresh)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun startTracking() {
        Log.d("ActivityTrackingService", "Tracking started.")

    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("ActivityTrackingService", "Tracking stopped.")
    }
}

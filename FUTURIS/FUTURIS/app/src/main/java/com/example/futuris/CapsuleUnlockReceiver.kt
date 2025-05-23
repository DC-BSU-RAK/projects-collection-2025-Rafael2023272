package com.example.futuris

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat

// This class listens for broadcast events, typically from an AlarmManager,
// to notify the user when a time capsule is unlocked.
class CapsuleUnlockReceiver : BroadcastReceiver() {

    // This method is triggered automatically when the broadcast is received.
    override fun onReceive(context: Context, intent: Intent?) {
        val channelId = "capsule_channel" // ID used to link the notification to a specific channel

        // Get the system NotificationManager to send notifications
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Notification channels are required on Android 8.0 (API 26) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a new notification channel with high importance for visibility
            val channel = NotificationChannel(
                channelId,                      // Unique channel ID
                "Capsule Notifications",        // Channel name (shown in system settings)
                NotificationManager.IMPORTANCE_HIGH // Importance level (makes sound, shows popup)
            )
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Your Time Capsule is Ready!")       // Main title
            .setContentText("You can now open your saved message.") // Subtext/message
            .setSmallIcon(R.drawable.ic_launcher_foreground)      // Small icon displayed in the status bar
            .setAutoCancel(true)                                  // Dismiss the notification when tapped
            .build()                                               // Finalize the notification object

        // Show the notification to the user
        notificationManager.notify(1, notification)
        // The first argument is a unique ID for the notification (used if we want to update or remove it later)
    }
}

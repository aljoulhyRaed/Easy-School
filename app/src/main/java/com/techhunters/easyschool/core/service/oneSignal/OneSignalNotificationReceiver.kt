package com.techhunters.easyschool.core.service.oneSignal

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.techhunters.easyschool.MainActivity

class OneSignalNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "com.onesignal.NotificationReceived") {
            val notificationId = intent.getIntExtra("notificationId", 0)
            val title = intent.getStringExtra("title")
            val message = intent.getStringExtra("alert")
            Toast.makeText(context, "New notification received: $title - $message", Toast.LENGTH_SHORT).show()
        } else if (intent.action == "com.onesignal.NotificationOpened") {
            val notificationId = intent.getIntExtra("notificationId", 0)
            val title = intent.getStringExtra("title")
            val message = intent.getStringExtra("alert")
            Toast.makeText(context, "Notification opened: $title - $message", Toast.LENGTH_SHORT).show()
            val openIntent = Intent(context, MainActivity::class.java)
            openIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(openIntent)
        }
    }
}
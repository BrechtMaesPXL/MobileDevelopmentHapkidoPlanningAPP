package com.example.hapkidoplanningapp.service
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.hapkidoplanningapp.MainActivity
import com.example.hapkidoplanningapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FirebaseService", "From: ${remoteMessage.from}")

        // Check if the message contains a notification payload
        remoteMessage.notification?.let {
            // If notification payload is present, display a notification
            sendNotification(it.title ?: "No Title", it.body ?: "No Body")
        }

        // Check if the message contains a data payload
        if (remoteMessage.data.isNotEmpty()) {
            // Handle data payload separately
            handleDataPayload(remoteMessage.data)
        }
    }

    private fun sendNotification(title: String, messageBody: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, "A1")
            .setSmallIcon(R.drawable._ebbb725_9404_4ba8_b834_066f621622bb) // Replace with your notification icon
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default_channel_id",
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Log and handle the new token here
        Log.d("FirebaseService", "Refreshed token: $token")

        // Implement logic to send the token to your server
        sendTokenToServer(token)
    }

    private fun sendTokenToServer(token: String) {
        // Implement your logic to send the token to your server
        // Example: using Retrofit to post the token to your server
        // Retrofit setup and API call logic goes here
    }
    private fun handleDataPayload(data: Map<String, String>) {
        // Handle the data payload here
        Log.d("FirebaseService", "Data payload: $data")
    }

}
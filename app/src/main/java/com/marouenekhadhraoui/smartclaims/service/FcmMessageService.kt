package com.marouenekhadhraoui.smartclaims.service


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.ui.main.MainActivity
import com.marouenekhadhraoui.smartclaims.utils.NOTIFICATION_CHANNEL_ID
import timber.log.Timber


class FcmMessageService : FirebaseMessagingService() {

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Timber.log(1, "received")
        if (remoteMessage.data.isNotEmpty()) {
            val extras = Bundle()
            for ((key, value) in remoteMessage.data) {
                extras.putString(key, value)
            }
            if (extras.containsKey("message") && !extras.getString("message").isNullOrBlank()) {
                sendNotification(extras.getString("message")!!)
            }
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String) {
        var chronoText = "-:-:-"
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)


        var notificationBuilder: NotificationCompat.Builder? = null
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                packageName,
                packageName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = packageName
            notificationManager.createNotificationChannel(channel)
            if (notificationBuilder == null) {
                notificationBuilder = NotificationCompat.Builder(application, packageName)
            }
        } else {
            if (notificationBuilder == null) {
                notificationBuilder = NotificationCompat.Builder(application, packageName)
            }
        }
        val customView =
            RemoteViews(packageName, R.layout.notification_expanded)
        customView.setTextViewText(
            R.id.notif_txt_timeclock_chrono,
            chronoText
        )
        val builder = NotificationCompat.Builder(
            this,
            NOTIFICATION_CHANNEL_ID
        )
        builder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setShowWhen(false)
            .setCustomContentView(customView)
            .setCustomBigContentView(customView)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .setOnlyAlertOnce(true)
            .setVibrate(longArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0))

        notificationManager.notify(0 /* ID of notification */, builder.build())
    }

}
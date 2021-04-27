package com.marouenekhadhraoui.smartclaims.service


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
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
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Timber.log(1, "received")
        if (remoteMessage.data.isNotEmpty()) {

            sendNotification("ddd")

        }
    }
    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun sendNotification(messageBody: String) {
        var chronoText = "-:-:-"
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)



        var notificationBuilder: NotificationCompat.Builder? = null
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val name = "name"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = packageName
            notificationManager.createNotificationChannel(channel)
            if (notificationBuilder == null) {
                notificationBuilder =
                    NotificationCompat.Builder(application, NOTIFICATION_CHANNEL_ID)
            }
        } else {
            if (notificationBuilder == null) {
                notificationBuilder =
                    NotificationCompat.Builder(application, NOTIFICATION_CHANNEL_ID)
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            val timer = object : CountDownTimer(604800000, 1000) {
                override fun onFinish() {

                }

                override fun onTick(millisUntilFinished: Long) {
                    try {
                        //now = TrueTimeRx.now()
                        var diff = millisUntilFinished
                        val secondsInMilli: Long = 1000
                        val minutesInMilli = secondsInMilli * 60
                        val hoursInMilli = minutesInMilli * 60
                        val daysInMilli = hoursInMilli * 24

                        val elapsedDays = diff / daysInMilli
                        diff %= daysInMilli

                        val elapsedHours = diff / hoursInMilli
                        diff %= hoursInMilli

                        val elapsedMinutes = diff / minutesInMilli
                        diff %= minutesInMilli

                        val elapsedSeconds = diff / secondsInMilli
                        chronoText = String.format(
                            "%02d:%02d:%02d:%02d",
                            elapsedDays,
                            elapsedHours,
                            elapsedMinutes,
                            elapsedSeconds
                        )
                        Timber.log(1, chronoText)
                    } catch (ex: Exception) {
                    }
                    val customView =
                        RemoteViews(packageName, R.layout.notification_expanded)
                    customView.setImageViewResource(R.id.notif_blue, R.drawable.ic_timer_blue)
                    customView.setImageViewResource(
                        R.id.notif_play_pause_button,
                        R.drawable.ic_logo_white
                    )
                    customView.setTextViewText(
                        R.id.notif_txt_timeclock_chrono,
                        chronoText
                    )
                    val notif = notificationBuilder
                        .setSmallIcon(R.drawable.ic_upload_notifications)
                        .setShowWhen(false)
                        .setCustomContentView(customView)
                        .setCustomBigContentView(customView)
                        .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setOngoing(true)
                        .setOnlyAlertOnce(true)
                        .setVibrate(longArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0))
                        .build()
                    notif.flags = Notification.FLAG_ONGOING_EVENT
                    startForeground(1 /* ID of notification */, notif)
                    // showNotificationPointeuseIfNotShown()
                }
            }
            timer.start()

        }, 0)


    }

}
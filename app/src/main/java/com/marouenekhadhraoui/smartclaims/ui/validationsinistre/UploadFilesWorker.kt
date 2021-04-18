package com.marouenekhadhraoui.smartclaims.ui.validationsinistre

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.work.ForegroundInfo
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.utils.*


class UploadFilesWorker(ctx: Context, params: WorkerParameters) : ListenableWorker(ctx, params) {

    override fun startWork(): ListenableFuture<Result> {
        var scan1 = inputData.getString(SCAN1)
        val scan2 = inputData.getString(SCAN2)
        val vid1 = inputData.getString(VID1)
        val vid2 = inputData.getString(VID2)
        val degat1 = inputData.getString(DEGAT1)
        val degat2 = inputData.getString(DEGAT2)
        val degat3 = inputData.getString(DEGAT3)
        val degat4 = inputData.getString(DEGAT4)
        val id = inputData.getString(ID)

        val storage = Firebase.storage
        val storageRef = storage.reference

        val progress = "Starting Download"
        setForegroundAsync(createForegroundInfo(progress))

        val scan1ref = storageRef.child("$id/images/scan1.jpg")
        val scan2ref = storageRef.child("$id/images/scan2.jpg")
        val vid1ref = storageRef.child("$id/videos/vid1.mp4")
        val vid2ref = storageRef.child("$id/videos/vid2.mp4")
        val degat1ref = storageRef.child("$id/images/degat1.jpg")
        val degat2ref = storageRef.child("$id/images/degat2.jpg")
        val degat3ref = storageRef.child("$id/images/degat3.jpg")
        val degat4ref = storageRef.child("$id/images/degat4.jpg")


        return CallbackToFutureAdapter.getFuture { completer ->

            scan1ref.putFile(scan1!!.toUri()).addOnFailureListener {
                // Handle unsuccessful uploads
                completer.setException(it)
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
                scan2ref.putFile(scan2!!.toUri()).addOnFailureListener {
                    // Handle unsuccessful uploads
                    completer.setException(it)
                }.addOnSuccessListener { taskSnapshot ->
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                    vid1ref.putFile(vid1!!.toUri()).addOnFailureListener {
                        completer.setException(it)
                        // Handle unsuccessful uploads
                    }.addOnSuccessListener { taskSnapshot ->
                        // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                        // ...
                        vid2ref.putFile(vid2!!.toUri()).addOnFailureListener {
                            completer.setException(it)
                            // Handle unsuccessful uploads
                        }.addOnSuccessListener { taskSnapshot ->
                            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                            // ...

                            degat1ref.putFile(degat1!!.toUri()).addOnFailureListener {
                                completer.setException(it)
                                // Handle unsuccessful uploads
                            }.addOnSuccessListener { taskSnapshot ->
                                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                                // ...

                                degat2ref.putFile(degat2!!.toUri()).addOnFailureListener {
                                    completer.setException(it)
                                    // Handle unsuccessful uploads
                                }.addOnSuccessListener { taskSnapshot ->
                                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                                    // ...
                                    degat3ref.putFile(degat3!!.toUri()).addOnFailureListener {
                                        completer.setException(it)
                                        // Handle unsuccessful uploads
                                    }.addOnSuccessListener { taskSnapshot ->
                                        // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                                        // ...

                                        degat4ref.putFile(degat4!!.toUri()).addOnFailureListener {
                                            completer.setException(it)
                                            // Handle unsuccessful uploads
                                        }.addOnSuccessListener { taskSnapshot ->
                                            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                                            // ...

                                            completer.set(Result.success())
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }


    private fun createForegroundInfo(progress: String): ForegroundInfo {
        val title = "upload en cours"
        val cancel = "cancel"
        // This PendingIntent can be used to cancel the worker
        val intent = WorkManager.getInstance(applicationContext)
                .createCancelPendingIntent(id)

        // Create a Notification channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setTicker(title)
                .setContentText(progress)
                .setSmallIcon(R.drawable.ic_upload_notifications)
                .setOngoing(true)
                // Add the cancel action to the notification which can
                // be used to cancel the worker
                .addAction(android.R.drawable.ic_delete, cancel, intent)
                .build()

        return ForegroundInfo(1, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
                vibrationPattern = longArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
            }
            channel.enableVibration(false)
            val notificationManager: NotificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val KEY_INPUT_URL = "KEY_INPUT_URL"
        const val KEY_OUTPUT_FILE_NAME = "KEY_OUTPUT_FILE_NAME"
    }

}
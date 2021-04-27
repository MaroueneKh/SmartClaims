package com.marouenekhadhraoui.smartclaims.ui.countdown

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_count_down.*
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class CountDownActivity : AppCompatActivity() {
    @Inject
    lateinit var logger: Logger

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)

        val bundle: Bundle? = intent.extras

        val message = bundle?.getString("date") // 1
        val dateTime = LocalDateTime.parse(
            message,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'.000Z'")
        )


        val duration = Duration.between(dateTime.plusHours(1), LocalDateTime.now())


        logger.log(duration.toString())


        val timer = object : CountDownTimer(604800000 - duration.toMillis() + 5000, 1000) {
            override fun onFinish() {

            }

            override fun onTick(millisUntilFinished: Long) {
                try {
                    //now = TrueTimeRx.now()
                    message
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


                    daysText.text = elapsedDays.toString()
                    hoursText.text = elapsedHours.toString()
                    secondsText.text = elapsedMinutes.toString()
                    minutesText.text = elapsedSeconds.toString()

                } catch (ex: Exception) {
                }
            }
        }
        timer.start()


    }

}
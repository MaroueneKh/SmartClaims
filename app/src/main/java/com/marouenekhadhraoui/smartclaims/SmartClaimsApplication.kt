package com.marouenekhadhraoui.smartclaims

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SmartClaimsApplication : Application() {
    @Inject
    lateinit var logger: Logger

    override fun onCreate() {
        super.onCreate()
        logger.configure()
    }
}
package com.marouenekhadhraoui.smartclaims.ui.splash


import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.marouenekhadhraoui.smartclaims.data.local.Datapreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(apprefs: Datapreferences) : ViewModel(), LifecycleObserver {


    val isConnected = apprefs.status.asLiveData()
    val isdarkenabled = apprefs.darkmode.asLiveData()


}
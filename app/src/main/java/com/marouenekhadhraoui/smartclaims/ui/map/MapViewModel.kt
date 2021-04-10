package com.marouenekhadhraoui.smartclaims.ui.map

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.newtork.NetworkHelper
import com.marouenekhadhraoui.smartclaims.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class MapViewModel @Inject constructor(var logger: Logger, var networkHelper: NetworkHelper) : ViewModel(), LifecycleObserver {

    var locationDone = MutableLiveData<Boolean>()

    var isConnected = MutableLiveData<Boolean>()

    init {

        locationDone.postValue(false)
        isConnected.postValue(networkHelper.isNetworkConnected())
    }

    private val _pressBtnSuivantEvent = MutableLiveData<Event<Unit>>()
    val pressBtnSuivantEvent: LiveData<Event<Unit>> = _pressBtnSuivantEvent


    fun pressBtnSuivant() {
        _pressBtnSuivantEvent.value = Event(Unit)
    }


}
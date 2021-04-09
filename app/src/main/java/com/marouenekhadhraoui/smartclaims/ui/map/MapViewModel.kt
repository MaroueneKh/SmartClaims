package com.marouenekhadhraoui.smartclaims.ui.map

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaims.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(var logger: Logger, private val apprefs: Datapreferences) : ViewModel(), LifecycleObserver {

    var locationDone = MutableLiveData<Boolean>()

    init {
        locationDone.postValue(false)
    }

    private val _pressBtnSuivantEvent = MutableLiveData<Event<Unit>>()
    val pressBtnSuivantEvent: LiveData<Event<Unit>> = _pressBtnSuivantEvent

    fun pressBtnSuivant() {
        _pressBtnSuivantEvent.value = Event(Unit)
    }


}
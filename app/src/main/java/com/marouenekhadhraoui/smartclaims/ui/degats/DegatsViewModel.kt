package com.marouenekhadhraoui.smartclaims.ui.degats

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marouenekhadhraoui.smartclaims.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DegatsViewModel @Inject constructor() : ViewModel(),
        LifecycleObserver {


    var _pressGalleryEvent = MutableLiveData<Event<Unit>>()
    var pressGaleryEvent: LiveData<Event<Unit>> = _pressGalleryEvent

    var _pressCameraEvent = MutableLiveData<Event<Unit>>()
    var pressCameraEvent: LiveData<Event<Unit>> = _pressCameraEvent

    private val _pressBtnScanEvent = MutableLiveData<Event<Unit>>()
    var pressBtnScanEvent: LiveData<Event<Unit>> = _pressBtnScanEvent

    private val _pressBtnSuivantEvent = MutableLiveData<Event<Unit>>()
    var pressBtnSuivantEvent: LiveData<Event<Unit>> = _pressBtnSuivantEvent


    fun clickonScan() {
        _pressBtnScanEvent.value = Event(Unit)
    }

    fun clickonSuivant() {
        _pressBtnSuivantEvent.value = Event(Unit)
    }


}
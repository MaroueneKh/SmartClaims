package com.marouenekhadhraoui.smartclaims.ui.videoSinistre

import android.net.Uri
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marouenekhadhraoui.smartclaims.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class VideoSinistreViewModel @Inject constructor() : ViewModel(),
        LifecycleObserver {


    var _pressGalleryEvent = MutableLiveData<Event<Unit>>()
    var pressGaleryEvent: LiveData<Event<Unit>> = _pressGalleryEvent

    var _pressCameraEvent = MutableLiveData<Event<Unit>>()
    var pressCameraEvent: LiveData<Event<Unit>> = _pressCameraEvent

    private val _pressBtnTakeVideoEvent = MutableLiveData<Event<Unit>>()
    var pressBtnTakeVideoEvent: LiveData<Event<Unit>> = _pressBtnTakeVideoEvent

    private val _pressBtnSuivantEvent = MutableLiveData<Event<Unit>>()
    var pressBtnSuivantEvent: LiveData<Event<Unit>> = _pressBtnSuivantEvent

    var uri1 = MutableLiveData<Uri>()
    var uri2 = MutableLiveData<Uri>()

    fun clickonTakeVideo() {
        _pressBtnTakeVideoEvent.value = Event(Unit)
    }

    fun clickonSuivant() {
        _pressBtnSuivantEvent.value = Event(Unit)
    }


}
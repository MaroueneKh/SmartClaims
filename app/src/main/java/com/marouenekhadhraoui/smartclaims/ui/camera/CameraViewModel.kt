package com.marouenekhadhraoui.smartclaims.ui.camera

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marouenekhadhraoui.smartclaims.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel(),
        LifecycleObserver {


    var _pressBtnConfirmEvent = MutableLiveData<Event<Unit>>()
    var pressBtnConfirmEvent: LiveData<Event<Unit>> = _pressBtnConfirmEvent

    private val _pressBtnCancelEvent = MutableLiveData<Event<Unit>>()
    var pressBtnCancelEvent: LiveData<Event<Unit>> = _pressBtnCancelEvent

    private val _pressBtnTakePicEvent = MutableLiveData<Event<Unit>>()
    var pressBtnTakePicEvent: LiveData<Event<Unit>> = _pressBtnTakePicEvent

    private val _pressBtnBackEvent = MutableLiveData<Event<Unit>>()
    var pressBtnBackEvent: LiveData<Event<Unit>> = _pressBtnBackEvent

    private val _pressBtnSetFlashEvent = MutableLiveData<Event<Unit>>()
    var pressBtnSetFlashEvent: LiveData<Event<Unit>> = _pressBtnSetFlashEvent

    fun confirmPicture() {
        _pressBtnConfirmEvent.value = Event(Unit)
    }

    fun cancelPicture() {
        _pressBtnCancelEvent.value = Event(Unit)
    }

    fun takePicture() {
        _pressBtnTakePicEvent.value = Event(Unit)
    }

    fun backtoPreview() {
        _pressBtnBackEvent.value = Event(Unit)
    }

    fun setFlash() {
        _pressBtnSetFlashEvent.value = Event(Unit)
    }


}
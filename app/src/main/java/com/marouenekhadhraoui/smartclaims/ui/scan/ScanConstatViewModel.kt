package com.marouenekhadhraoui.smartclaims.ui.scan


import android.net.Uri
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marouenekhadhraoui.smartclaims.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScanConstatViewModel @Inject constructor() : ViewModel(),
        LifecycleObserver {


    var _pressGalleryEvent = MutableLiveData<Event<Unit>>()
    var pressGaleryEvent: LiveData<Event<Unit>> = _pressGalleryEvent

    private val _pressBtnScanEvent = MutableLiveData<Event<Unit>>()
    var pressBtnScanEvent: LiveData<Event<Unit>> = _pressBtnScanEvent

    var uri1 = MutableLiveData<Uri>()
    var uri2 = MutableLiveData<Uri>()

    fun clickonScan() {
        _pressBtnScanEvent.value = Event(Unit)
    }


}
package com.marouenekhadhraoui.smartclaims.ui.scan


import android.content.Context
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaims.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaims.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanConstatViewModel @Inject constructor(private val apprefs: Datapreferences) : ViewModel(),
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

    fun saveScan(context: Context, scan1: String, scan2: String) {
        viewModelScope.launch {
            try {
                apprefs.setscan1(context, scan1)
                apprefs.setscan2(context, scan2)
            } catch (exception: Exception) {


            }
        }

    }


}
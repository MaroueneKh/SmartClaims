package com.marouenekhadhraoui.smartclaims.ui.videoSinistre

import android.content.Context
import android.net.Uri
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaims.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaims.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VideoSinistreViewModel @Inject constructor(private val apprefs: Datapreferences) :
    ViewModel(),
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

    fun saveVideo(context: Context, vid1: String, vid2: String) {
        viewModelScope.launch {
            try {
                apprefs.setvid1(context, vid1)
                apprefs.setvid2(context, vid2)
            } catch (exception: Exception) {


            }
        }

    }


}
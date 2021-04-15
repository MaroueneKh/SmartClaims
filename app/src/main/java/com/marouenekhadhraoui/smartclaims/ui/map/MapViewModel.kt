package com.marouenekhadhraoui.smartclaims.ui.map

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaims.newtork.NetworkHelper
import com.marouenekhadhraoui.smartclaims.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class MapViewModel @Inject constructor(
    var logger: Logger,
    var networkHelper: NetworkHelper,
    private val apprefs: Datapreferences
) : ViewModel(), LifecycleObserver {

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

    fun saveLocation(context: Context, lat: String, long: String) {
        viewModelScope.launch {
            try {
                apprefs.setlat(context, lat)
                apprefs.setlong(context, long)
            } catch (exception: Exception) {
                logger.log("catch ")
                logger.log(exception.message.toString())

            }
        }

    }


}
package com.marouenekhadhraoui.smartclaims.ui.validationsinistre

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaims.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaims.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ValidationSinistreViewModel @Inject constructor(
    private val apprefs: Datapreferences
) : ViewModel(),
    LifecycleObserver {


    var list1: ArrayList<Uri> = ArrayList()
    var list2: ArrayList<Uri> = ArrayList()


    private val _pressBtnSuivantEvent = MutableLiveData<Event<Unit>>()
    var pressBtnSuivantEvent: LiveData<Event<Unit>> = _pressBtnSuivantEvent

    fun clickonSuivant() {
        _pressBtnSuivantEvent.value = Event(Unit)
    }


    fun getScan1() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.scan1.collect { scan1 ->
                list1.add(scan1.toUri())


            }

        }
    }

    fun getScan2() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.scan2.collect { scan2 ->
                list1.add(scan2.toUri())


            }

        }
    }

    fun getVid1() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.vid1.collect { vid1 ->
                list1.add(vid1.toUri())

            }

        }
    }

    fun getVid2() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.vid2.collect { vid2 ->
                list1.add(vid2.toUri())


            }

        }
    }

    fun getDegats1() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.degats1.collect { degat1 ->
                list2.add(degat1.toUri())


            }

        }
    }

    fun getDegats2() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.degats1.collect { degat2 ->
                list2.add(degat2.toUri())


            }

        }
    }

    fun getDegats3() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.degats1.collect { degat3 ->
                list2.add(degat3.toUri())


            }

        }
    }

    fun getDegats4() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.degats1.collect { degat4 ->
                list2.add(degat4.toUri())


            }

        }
    }

}

//apprefs.degats1.collect { degat1 ->
// Update View with the latest favorite news
//  list2.add(degat1.toUri())
// logger.log(degat1)
//}

//  apprefs.degats3.collect { degat3 ->
// Update View with the latest favorite news
//    list2.add(degat3.toUri())
//  logger.log(degat3)
//}
//apprefs.degats4.collect { degat4 ->
// Update View with the latest favorite news
//  list2.add(degat4.toUri())
// logger.log(degat4)
// }






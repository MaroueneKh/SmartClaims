package com.marouenekhadhraoui.smartclaims.ui.validationsinistre

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaims.newtork.NetworkHelper
import com.marouenekhadhraoui.smartclaims.ui.signup.SignupModel
import com.marouenekhadhraoui.smartclaims.utils.Event
import com.marouenekhadhraoui.smartclaims.utils.Resource
import com.marouenekhadhraoui.smartclaims.utils.internetErr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ValidationSinistreViewModel @Inject constructor(
        private val apprefs: Datapreferences,
        private val validationrepo: ValdiationRepository,
        private val logger: Logger,
        var networkHelper: NetworkHelper
) : ViewModel(),
        LifecycleObserver {


    var list1: ArrayList<String> = ArrayList()
    var list2: ArrayList<String> = ArrayList()

    lateinit var type: String
    lateinit var lat: String
    lateinit var lang: String


    private val _pressBtnSuivantEvent = MutableLiveData<Event<Unit>>()
    var pressBtnSuivantEvent: LiveData<Event<Unit>> = _pressBtnSuivantEvent

    private val _uploadDone = MutableLiveData<Boolean>()
    var uploadDone: LiveData<Boolean> = _uploadDone

    private val _idNewDossier = MutableLiveData<Resource<List<SignupModel>>>()
    var idNewDossier: LiveData<Resource<List<SignupModel>>> = _idNewDossier


    init {
        _uploadDone.value = false
    }

    fun clickonSuivant() {
        _pressBtnSuivantEvent.value = Event(Unit)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getIdNewDossier() {

        if (!networkHelper.isNetworkConnected()) {
            logger.log("pas de connexion")
            _idNewDossier.postValue(
                    Resource.error(
                            data = null,
                            message = internetErr))
        } else {
            logger.log("thama connexion")
            viewModelScope.launch {
                try {
                    logger.log("collecting data")
                    apprefs.token.collect { token ->
                        logger.log("new dossier")
                        _idNewDossier.value = Resource.success(
                                data = validationrepo.newDossier(token, type, lat, lang)
                        )
                    }
                } catch (exception: Exception) {
                    logger.log("catch")
                    logger.log(exception.message.toString())
                }
            }

        }

    }


    fun uploadFiles(id: String) {
        logger.log(list1[1].toString())
        logger.log(list1[2].toString())
        viewModelScope.launch {
            try {

                //    _uploadDone.postValue(validationrepo.uploadFiles(list1,list2,id))
            } catch (exception: Exception) {
                logger.log("catch")
                logger.log(exception.message.toString())
            }
        }


    }

    fun gettype() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.type.collect { scan1 ->
                type = scan1


            }

        }
    }

    fun getlat() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.lat.collect { scan1 ->
                lat = scan1


            }

        }
    }

    fun getlang() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.long.collect { scan1 ->
                lang = scan1


            }

        }
    }

    fun getScan1() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.scan1.collect { scan1 ->
                list1.add(scan1)


            }

        }
    }

    fun getScan2() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.scan2.collect { scan2 ->
                list1.add(scan2)


            }

        }
    }

    fun getVid1() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.vid1.collect { vid1 ->
                list1.add(vid1)

            }

        }
    }

    fun getVid2() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.vid2.collect { vid2 ->
                list1.add(vid2)


            }

        }
    }

    fun getDegats1() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.degats1.collect { degat1 ->
                list2.add(degat1)


            }

        }
    }
    fun getDegats2() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.degats2.collect { degat2 ->
                list2.add(degat2)
            }

        }
    }
    fun getDegats3() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.degats3.collect { degat3 ->
                list2.add(degat3)
            }

        }
    }
    fun getDegats4() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            apprefs.degats4.collect { degat4 ->
                list2.add(degat4)
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






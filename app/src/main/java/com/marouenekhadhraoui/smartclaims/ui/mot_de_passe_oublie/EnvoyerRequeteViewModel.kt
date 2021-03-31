package com.marouenekhadhraoui.smartclaims.ui.mot_de_passe_oublie

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.newtork.NetworkHelper
import com.marouenekhadhraoui.smartclaims.ui.signin.AssureModel
import com.marouenekhadhraoui.smartclaims.ui.signin.AssureRepository
import com.marouenekhadhraoui.smartclaims.ui.validators.LiveDataValidator
import com.marouenekhadhraoui.smartclaims.ui.validators.LiveDataValidatorResolver
import com.marouenekhadhraoui.smartclaims.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class EnvoyerRequeteViewModel @Inject constructor(private val assureRepository: AssureRepository, var networkHelper: NetworkHelper, var logger: Logger) : ViewModel(), LifecycleObserver {

    val isLoginFormValidMediator = MediatorLiveData<Boolean>()
    var Mail = MutableLiveData<String>()

    private val _request = MutableLiveData<Resource<List<AssureModel>>>()

    private val _pressBtnRequestEvent = MutableLiveData<Event<Unit>>()
    val pressBtnRequestEvent: LiveData<Event<Unit>> = _pressBtnRequestEvent


    // public
    val request: LiveData<Resource<List<AssureModel>>> = _request

    init {
        if (!networkHelper.isNetworkConnected()) {
            logger.log("pas de connexion")
            _request.postValue(
                    Resource.error(
                            data = null,
                            message = internetErr))

        }
        isLoginFormValidMediator.value = false
        isLoginFormValidMediator.addSource(Mail) { validateForm() }
        _request.postValue(Resource.loading(null))


    }

    val MailValidator = LiveDataValidator(Mail).apply {
        //Whenever the condition of the predicate is true, the error message should be emitted
        addRule("Mail is required") { it.isNullOrBlank() }
        addRule("Wrong mail form") { it!!.matches(EMAIL_REGEX.toRegex()) }
    }

    fun validateForm() {
        val validators = listOf(MailValidator)
        val validatorResolver = LiveDataValidatorResolver(validators)
        isLoginFormValidMediator.value = validatorResolver.isValid()
    }


    fun pressBtnRequete() {
        _pressBtnRequestEvent.value = Event(Unit)
        //check network
        if (!networkHelper.isNetworkConnected()) {
            logger.log("pas de connexion")
            _request.postValue(
                    Resource.error(
                            data = null,
                            message = internetErr))
        } else {
            viewModelScope.launch {
                try {
                    logger.log("mail" + " = " + Mail.value.toString())
                    _request.value = Resource.success(
                            data = assureRepository.sendRequest(Mail.value.toString()))
                } catch (exception: Exception) {
                    logger.log("catch")
                    logger.log(exception.message.toString())

                    _request.value = Resource.error(
                            data = null,
                            message = exception.message ?: otherErr
                    )
                }
            }

        }


    }


}
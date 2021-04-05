package com.marouenekhadhraoui.smartclaims.ui.signup

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaims.newtork.NetworkHelper

import com.marouenekhadhraoui.smartclaims.ui.signin.AssureRepository
import com.marouenekhadhraoui.smartclaims.ui.validators.LiveDataValidator
import com.marouenekhadhraoui.smartclaims.ui.validators.LiveDataValidatorResolver
import com.marouenekhadhraoui.smartclaims.utils.*

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class SignUpViewModel @Inject constructor(private val assureRepository: AssureRepository, var networkHelper: NetworkHelper, var logger: Logger, private val apprefs: Datapreferences) : ViewModel(), LifecycleObserver {

    val isLoginFormValidMediator = MediatorLiveData<Boolean>()
    var Contrat = MutableLiveData<String>()
    var Cin = MutableLiveData<String>()
    var Mail = MutableLiveData<String>()
    private val _pressBtnSeconnecterEvent = MutableLiveData<Event<Unit>>()
    val pressBtnSeconnecterEvent: LiveData<Event<Unit>> = _pressBtnSeconnecterEvent
    private val _pressBtnSinscrireEvent = MutableLiveData<Event<Unit>>()
    val pressBtnSinscrireEvent: LiveData<Event<Unit>> = _pressBtnSinscrireEvent

    // cached
    private val _newassure = MutableLiveData<Resource<List<SignupModel>>>()

    // public
    val newassure: LiveData<Resource<List<SignupModel>>> = _newassure

    init {
        if (!networkHelper.isNetworkConnected()) {
            logger.log("pas de connexion")
            _newassure.postValue(
                    Resource.error(
                            data = null,
                            message = internetErr))

        }
        isLoginFormValidMediator.value = false

        isLoginFormValidMediator.addSource(Contrat) { validateForm() }
        isLoginFormValidMediator.addSource(Cin) { validateForm() }
        isLoginFormValidMediator.addSource(Mail) { validateForm() }
        _newassure.postValue(Resource.loading(null))


    }

    val ContratValidator = LiveDataValidator(Contrat).apply {
        //Whenever the condition of the predicate is true, the error message should be emitted
        addRule("Contrat is required") { it.isNullOrBlank() }
    }

    val CinValidator = LiveDataValidator(Cin).apply {
        //Whenever the condition of the predicate is true, the error message should be emitted
        addRule("CIN is required") { it.isNullOrBlank() }
        addRule("CIN must be 8 numbers") { it!!.length != 8 }
    }
    val MailValidator = LiveDataValidator(Mail).apply {
        //Whenever the condition of the predicate is true, the error message should be emitted
        addRule("Mail is required") { it.isNullOrBlank() }
        addRule("Wrong mail form") { it!!.matches(EMAIL_REGEX.toRegex()) }
    }

    fun validateForm() {
        val validators = listOf(ContratValidator, CinValidator, MailValidator)
        val validatorResolver = LiveDataValidatorResolver(validators)
        isLoginFormValidMediator.value = validatorResolver.isValid()
    }

    fun pressBtnSinscrire() {
        _pressBtnSinscrireEvent.value = Event(Unit)
        //check network
        if (!networkHelper.isNetworkConnected()) {
            logger.log("pas de connexion")
            _newassure.postValue(
                    Resource.error(
                            data = null,
                            message = internetErr))
        } else {
            viewModelScope.launch {
                try {
                    _newassure.value = Resource.success(
                            data = assureRepository.postAssure(Mail.value.toString(), Contrat.value.toString()))
                } catch (exception: Exception) {
                    logger.log("catch")
                    logger.log(exception.message.toString())

                    _newassure.value = Resource.error(
                            data = null,
                            message = exception.message ?: otherErr
                    )
                }
            }

        }


    }

    fun saveToken(context: Context, token: String) {
        viewModelScope.launch {
            try {
                apprefs.setToken(context, token)
                apprefs.setStatus(context)
            } catch (exception: Exception) {
                logger.log("catch ")
                logger.log(exception.message.toString())

            }
        }

    }


    fun pressBtnSeConnceter() {
        _pressBtnSeconnecterEvent.value = Event(Unit)
    }


}
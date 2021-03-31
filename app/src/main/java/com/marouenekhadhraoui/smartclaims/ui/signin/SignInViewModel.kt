package com.marouenekhadhraoui.smartclaims.ui.signin


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaims.newtork.NetworkHelper
import com.marouenekhadhraoui.smartclaims.ui.validators.LiveDataValidator
import com.marouenekhadhraoui.smartclaims.ui.validators.LiveDataValidatorResolver
import com.marouenekhadhraoui.smartclaims.utils.Event
import com.marouenekhadhraoui.smartclaims.utils.Resource
import com.marouenekhadhraoui.smartclaims.utils.internetErr
import com.marouenekhadhraoui.smartclaims.utils.otherErr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class SignInViewModel @Inject constructor(private val assureRepository: AssureRepository, var networkHelper: NetworkHelper, var logger: Logger, private val apprefs: Datapreferences) : ViewModel(), LifecycleObserver {


    val isLoginFormValidMediator = MediatorLiveData<Boolean>()
    var Identifiant = MutableLiveData<String>()
    var Password = MutableLiveData<String>()

    //visibility of text view
    private val _warning = MutableLiveData<Boolean>()

    // public
    var warning: LiveData<Boolean> = _warning

    // cached
    private val _assure = MutableLiveData<Resource<List<AssureModel>>>()

    // public
    val assure: LiveData<Resource<List<AssureModel>>> = _assure

    init {
        if (!networkHelper.isNetworkConnected()) {
            logger.log("pas de connexion")
            _assure.postValue(
                    Resource.error(
                            data = null,
                            message = internetErr))
        }
        isLoginFormValidMediator.value = false
        _warning.value = false
        isLoginFormValidMediator.addSource(Identifiant) { validateForm() }
        isLoginFormValidMediator.addSource(Password) { validateForm() }
        _assure.postValue(Resource.loading(null))

    }

    private val _pressBtnSinscrireEvent = MutableLiveData<Event<Unit>>()
    val pressBtnSinscrireEvent: LiveData<Event<Unit>> = _pressBtnSinscrireEvent


    private val _pressLinkoublieEvent = MutableLiveData<Event<Unit>>()
    val ressLinkoublieEvent: LiveData<Event<Unit>> = _pressLinkoublieEvent

    val IdentifiantValidator = LiveDataValidator(Identifiant).apply {
        //Whenever the condition of the predicate is true, the error message should be emitted
        addRule("identifiant is required") { it.isNullOrBlank() }
    }

    val PasswordValidator = LiveDataValidator(Password).apply {
        //Whenever the condition of the predicate is true, the error message should be emitted
        addRule("password is required") { it.isNullOrBlank() }
        addRule("password length must be 10") { it!!.length < 5 }
    }


    private val _pressBtnSeConnecterEvent = MutableLiveData<Event<Unit>>()
    val pressBtnSeConnecterEvent: LiveData<Event<Unit>> = _pressBtnSeConnecterEvent


    fun validateForm() {
        val validators = listOf(IdentifiantValidator, PasswordValidator)
        val validatorResolver = LiveDataValidatorResolver(validators)
        isLoginFormValidMediator.value = validatorResolver.isValid()
    }

    fun clickonOublie() {
        _pressLinkoublieEvent.value = Event(Unit)
    }

    fun pressBtnSinscrire() {
        _pressBtnSinscrireEvent.value = Event(Unit)
    }

    fun pressBtnSeConnecter() {
        _pressBtnSeConnecterEvent.value = Event(Unit)
        //check network
        if (!networkHelper.isNetworkConnected()) {
            logger.log("pas de connexion")
            _assure.postValue(
                    Resource.error(
                            data = null,
                            message = internetErr))
        } else {
            viewModelScope.launch {
                try {
                    _assure.value = Resource.success(
                            data = assureRepository.getAssure(Identifiant.value.toString(), Password.value.toString()))
                } catch (exception: Exception) {
                    logger.log("catch")
                    logger.log(exception.message.toString())
                    _warning.value = true
                    _assure.value = Resource.error(
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
}
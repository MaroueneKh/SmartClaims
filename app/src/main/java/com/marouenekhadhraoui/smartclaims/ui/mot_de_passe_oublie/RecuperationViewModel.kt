package com.marouenekhadhraoui.smartclaims.ui.mot_de_passe_oublie

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marouenekhadhraoui.smartclaims.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RecuperationViewModel @Inject constructor() : ViewModel(), LifecycleObserver {


    private val _pressBtnParmailEvent = MutableLiveData<Event<Unit>>()
    val pressBtnParmailEvent: LiveData<Event<Unit>> = _pressBtnParmailEvent


    private val _pressBtnParsmsEvent = MutableLiveData<Event<Unit>>()
    val pressBtnParsmsEvent: LiveData<Event<Unit>> = _pressBtnParsmsEvent

    fun pressButtonMail() {
        _pressBtnParmailEvent.value = Event(Unit)

    }

    fun pressButtonSms() {
        _pressBtnParsmsEvent.value = Event(Unit)

    }


}
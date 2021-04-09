package com.marouenekhadhraoui.smartclaims.ui.declaration

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.data.local.Datapreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DeclarationViewModel @Inject constructor(var logger: Logger, private val apprefs: Datapreferences) : ViewModel(), LifecycleObserver {

    var stateButton1 = MutableLiveData<String>()
    var stateButton2 = MutableLiveData<String>()
    var stateButton3 = MutableLiveData<String>()
    var stateButton4 = MutableLiveData<String>()

    init {
        stateButton1.postValue("checked")

    }


}
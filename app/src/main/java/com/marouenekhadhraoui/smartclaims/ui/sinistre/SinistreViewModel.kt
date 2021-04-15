package com.marouenekhadhraoui.smartclaims.ui.sinistre

import android.content.Context
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.data.local.Datapreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SinistreViewModel @Inject constructor(
    var logger: Logger,
    private val apprefs: Datapreferences
) : ViewModel(), LifecycleObserver {

    fun saveType(context: Context, type: String) {
        viewModelScope.launch {
            try {
                apprefs.setType(context, type)
            } catch (exception: Exception) {
                logger.log("catch ")
                logger.log(exception.message.toString())

            }
        }

    }


}
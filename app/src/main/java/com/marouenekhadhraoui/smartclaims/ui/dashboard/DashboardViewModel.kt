package com.marouenekhadhraoui.smartclaims.ui.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaims.newtork.NetworkHelper
import com.marouenekhadhraoui.smartclaims.utils.Resource
import com.marouenekhadhraoui.smartclaims.utils.otherErr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dossierRepository: DossierRepository,
    var networkHelper: NetworkHelper,
    var logger: Logger,
    private val apprefs: Datapreferences
) : ViewModel(),
    LifecycleObserver {


    private val _dossier = MutableLiveData<Resource<List<DossierModel>>>()

    // public
    val dossier: LiveData<Resource<List<DossierModel>>> = _dossier

    private lateinit var tokenFetched: String

    init {
        logger.log("fil init")
        _dossier.value = Resource.loading(
            data = null,
        )
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {

                    logger.log("dkhalna lil try")
                    apprefs.token.collect { token ->
                        _dossier.value = Resource.success(
                            data = dossierRepository.getDossier(
                                token
                            )
                        )
                    }

                } catch (exception: Exception) {
                    logger.log("catch")
                    logger.log(exception.message.toString())
                    _dossier.value = Resource.error(
                        data = null,
                        message = exception.message ?: otherErr
                    )
                }
            }
        }


    }


}
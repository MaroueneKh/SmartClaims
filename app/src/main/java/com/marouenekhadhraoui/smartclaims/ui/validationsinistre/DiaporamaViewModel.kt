package com.marouenekhadhraoui.smartclaims.ui.validationsinistre


import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DiaporamaViewModel @Inject constructor(
) : ViewModel(),
    LifecycleObserver
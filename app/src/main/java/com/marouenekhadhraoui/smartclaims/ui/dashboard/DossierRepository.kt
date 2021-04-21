package com.marouenekhadhraoui.smartclaims.ui.dashboard

import com.marouenekhadhraoui.smartclaims.newtork.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DossierRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {


    suspend fun getDossier(token: String) = apiHelper.getDossier(token)


}
package com.marouenekhadhraoui.smartclaims.newtork

import com.marouenekhadhraoui.smartclaims.ui.dashboard.DossierModel
import com.marouenekhadhraoui.smartclaims.ui.signin.AssureModel
import com.marouenekhadhraoui.smartclaims.ui.signup.SignupModel
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getAssure(mail: String, password: String): List<AssureModel> = apiService.getAssure(mail, password)
    override suspend fun postAssure(mail: String, contrat: String): List<SignupModel> = apiService.postAssure(mail, contrat)
    override suspend fun sendRequest(mail: String): List<AssureModel> = apiService.sendRequest(mail)
    override suspend fun newDossier(
        token: String,
        type: String,
        lat: String,
        lang: String
    ): List<SignupModel> = apiService.newDossier(token, type, lat, lang)

    override suspend fun getDossier(token: String): List<DossierModel> =
        apiService.getDossier(token)

}



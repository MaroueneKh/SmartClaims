package com.marouenekhadhraoui.smartclaims.newtork

import com.marouenekhadhraoui.smartclaims.ui.signin.AssureModel
import com.marouenekhadhraoui.smartclaims.ui.signup.SignupModel


interface ApiHelper {

    suspend fun getAssure(mail: String, password: String): List<AssureModel>

    suspend fun postAssure(mail: String, contrat: String): List<SignupModel>

    suspend fun sendRequest(mail: String): List<AssureModel>

    suspend fun newDossier(token: String, type: String, lat: String, lang: String): List<SignupModel>
}
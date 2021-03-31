package com.marouenekhadhraoui.smartclaims.ui.signin

import com.marouenekhadhraoui.smartclaims.newtork.ApiHelper

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssureRepository @Inject constructor(
        private val apiHelper: ApiHelper
) {
    suspend fun getAssure(mail: String, password: String) = apiHelper.getAssure(mail, password)

    suspend fun postAssure(mail: String, contrat: String) = apiHelper.postAssure(mail, contrat)

    suspend fun sendRequest(mail: String) = apiHelper.sendRequest(mail)

    /*   fun checkifNull(assure:AssureModel): Boolean {
           return assure == null
      }
      */

}
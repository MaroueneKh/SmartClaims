package com.marouenekhadhraoui.smartclaims.newtork

import com.marouenekhadhraoui.smartclaims.ui.signin.AssureModel
import com.marouenekhadhraoui.smartclaims.ui.signup.SignupModel
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @Headers(
        "Content-Type: application/json"
    )
    @POST("api/assure")
    suspend fun getAssure(@Query(value = "identifiant") mail: String,
                          @Query(value = "password") password: String): List<AssureModel>

    @POST("api/newassure")
    suspend fun postAssure(@Query(value = "mail") mail: String,
                           @Query(value = "contrat") contrat: String): List<SignupModel>

    @POST("api/requestmail")
    suspend fun sendRequest(@Query(value = "mail") mail: String): List<AssureModel>

    @POST("api/newDossier")
    suspend fun newDossier(@Query(value = "token") mail: String,
                           @Query(value = "type") type: String,
                           @Query(value = "lat") lat: String,
                           @Query(value = "lang") lang: String
    ): List<SignupModel>


}
package com.marouenekhadhraoui.smartclaims.ui.signin

import com.google.gson.annotations.SerializedName

data class AssureModel(
        @SerializedName("contrat")
        val contrat: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("identifiant")
        val identifiant: String,
        @SerializedName("mail")
        val mail: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("token")
        val token: String


)
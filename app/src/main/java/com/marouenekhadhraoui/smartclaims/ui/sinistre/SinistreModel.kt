package com.marouenekhadhraoui.smartclaims.ui.sinistre

import androidx.annotation.DrawableRes

data class SinistreModel(
    @DrawableRes val image: Int,
    val title: String,
    val text: String

)
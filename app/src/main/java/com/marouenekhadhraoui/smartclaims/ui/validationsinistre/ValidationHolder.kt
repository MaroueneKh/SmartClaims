package com.marouenekhadhraoui.smartclaims.ui.validationsinistre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marouenekhadhraoui.smartclaims.R
import kotlinx.android.synthetic.main.layout_validation_sinistre.view.*

class ValidationHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) :
            this(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_validation_sinistre, parent, false)
            )

    fun bind(validation: ValidationModel) {
        itemView.image1.setImageURI(validation.uri1)
        itemView.image2.setImageURI(validation.uri2)

        if (validation.uri3.toString().endsWith("jpg")) {
            itemView.image3.setImageURI(validation.uri3)
            itemView.image4.setImageURI(validation.uri4)

        } else {
            Glide.with(itemView.context).load(validation.uri3).thumbnail(0.1f).into(itemView.image3)
            Glide.with(itemView.context).load(validation.uri4).thumbnail(0.1f).into(itemView.image4)

        }


    }
}
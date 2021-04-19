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
        Glide.with(itemView.context).load(validation.uri1).centerInside().into(itemView.image1)
        Glide.with(itemView.context).load(validation.uri2).centerInside().into(itemView.image2)

        if (validation.uri3.toString().endsWith("jpg")) {
            Glide.with(itemView.context).load(validation.uri3).centerInside().into(itemView.image3)
            Glide.with(itemView.context).load(validation.uri4).centerInside().into(itemView.image4)

        } else {
            Glide.with(itemView.context).load(validation.uri3).centerInside().thumbnail(0.8f)
                .into(itemView.image3)
            Glide.with(itemView.context).load(validation.uri4).centerInside().thumbnail(0.8f)
                .into(itemView.image4)

        }


    }
}
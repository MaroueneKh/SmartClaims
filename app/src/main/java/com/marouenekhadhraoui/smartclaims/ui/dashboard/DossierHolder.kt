package com.marouenekhadhraoui.smartclaims.ui.dashboard


import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marouenekhadhraoui.smartclaims.R
import kotlinx.android.synthetic.main.layout_dossier_item.view.*

class DossierHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) :
            this(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_dossier_item, parent, false)
            )
    @RequiresApi(Build.VERSION_CODES.M)
    fun bind(dossier: DossierModel) {
        itemView.textView9.text = "Dossier n " + dossier.id.toString()
        itemView.textView12.text = dossier.etat
        if (dossier.etat == "en cours de confirmation") {

            Glide.with(itemView.context).load(R.drawable.ic_eye_blue).centerInside()
                .into(itemView.img_logo9)

            itemView.textView12.setTextColor(itemView.context.getColor(R.color.orange))
        } else if (dossier.etat == "En attente du depot") {

            Glide.with(itemView.context).load(R.drawable.ic_groupe_16124).centerInside()
                .into(itemView.img_logo9)
            itemView.textView12.setTextColor(itemView.context.getColor(R.color.orange))

        }

    }
}
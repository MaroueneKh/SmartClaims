package com.marouenekhadhraoui.smartclaims.ui.validationsinistre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marouenekhadhraoui.smartclaims.R
import kotlinx.android.synthetic.main.layout_diaporama_item.view.*


class DiaporamaHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) :
            this(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_diaporama_item, parent, false)
            )

    fun bind(diaporama: DiaporamaModel) {
        if (diaporama.uri.toString().endsWith("jpg")) {
            itemView.video.visibility = View.GONE
            Glide.with(itemView.context).load(diaporama.uri).into(itemView.image)
        } else {
            itemView.image.visibility = View.GONE
            val mediaController = MediaController(itemView.context)
            mediaController.setAnchorView(itemView.video)
            itemView.video.setMediaController(mediaController)
            itemView.video.setVideoURI(diaporama.uri)
            itemView.video.requestFocus()
            itemView.video.start()
        }

    }
}
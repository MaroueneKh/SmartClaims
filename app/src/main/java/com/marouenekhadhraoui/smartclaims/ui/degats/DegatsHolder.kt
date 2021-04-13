package com.marouenekhadhraoui.smartclaims.ui.degats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaims.R
import kotlinx.android.synthetic.main.layout_image_scan_item.view.*

class DegatsHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) :
            this(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.layout_image_scan_item, parent, false)
            )

    fun bind(degat: DegatsModel) {
        itemView.imageScan.setImageURI(degat.image)

    }
}
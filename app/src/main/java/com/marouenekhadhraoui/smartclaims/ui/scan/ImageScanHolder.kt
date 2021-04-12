package com.marouenekhadhraoui.smartclaims.ui.scan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaims.R
import kotlinx.android.synthetic.main.layout_image_scan_item.view.*

class ImageScanHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) :
            this(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.layout_image_scan_item, parent, false)
            )

    fun bind(home: ImageScanModel) {
        itemView.imageScan.setImageResource(home.image)

    }
}
package com.marouenekhadhraoui.smartclaims.ui.scan

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class ImageScanAdapter @Inject constructor() : RecyclerView.Adapter<ImageScanHolder>() {
    var list: List<ImageScanModel> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageScanHolder {
        return ImageScanHolder(parent)
    }

    override fun onBindViewHolder(holder: ImageScanHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setItem(list: List<ImageScanModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}
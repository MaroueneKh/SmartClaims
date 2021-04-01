package com.marouenekhadhraoui.smartclaims.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaims.R
import kotlinx.android.synthetic.main.layout_home_item.view.*

class HomeHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) :
            this(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_home_item, parent, false)
            )

    fun bind(home: HomeModel) {
        itemView.textView6.text = home.text
        itemView.image.setImageResource(home.image)

    }
}
package com.marouenekhadhraoui.smartclaims.ui.sinistre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaims.R
import kotlinx.android.synthetic.main.layout_home_item.view.image
import kotlinx.android.synthetic.main.layout_home_item.view.textView6
import kotlinx.android.synthetic.main.layout_sinistre_item.view.*

class SinistreHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) :
            this(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_sinistre_item, parent, false)
            )

    fun bind(home: SinistreModel) {
        itemView.textView6.text = home.title
        itemView.image.setImageResource(home.image)
        itemView.textView7.text = home.text

    }
}
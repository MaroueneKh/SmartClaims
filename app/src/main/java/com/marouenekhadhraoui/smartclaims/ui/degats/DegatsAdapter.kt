package com.marouenekhadhraoui.smartclaims.ui.degats

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DegatsAdapter @Inject constructor() : RecyclerView.Adapter<DegatsHolder>() {
    var list: List<DegatsModel> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DegatsHolder {
        return DegatsHolder(parent)
    }

    override fun onBindViewHolder(holder: DegatsHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setItem(list: List<DegatsModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}
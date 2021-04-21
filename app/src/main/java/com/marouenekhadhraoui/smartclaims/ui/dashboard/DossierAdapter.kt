package com.marouenekhadhraoui.smartclaims.ui.dashboard

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DossierAdapter @Inject constructor() : RecyclerView.Adapter<DossierHolder>() {
    var list: List<DossierModel> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DossierHolder {
        return DossierHolder(parent)
    }

    override fun onBindViewHolder(holder: DossierHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setItem(list: List<DossierModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}
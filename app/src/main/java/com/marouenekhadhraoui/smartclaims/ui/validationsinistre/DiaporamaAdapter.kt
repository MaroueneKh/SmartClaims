package com.marouenekhadhraoui.smartclaims.ui.validationsinistre

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaims.ui.sinistre.SinistreViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiaporamaAdapter @Inject constructor() : RecyclerView.Adapter<DiaporamaHolder>() {
    var list: List<DiaporamaModel> = listOf()
    lateinit var viewModel: SinistreViewModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaporamaHolder {
        return DiaporamaHolder(parent)
    }

    override fun onBindViewHolder(holder: DiaporamaHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setItem(list: List<DiaporamaModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size


}
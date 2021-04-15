package com.marouenekhadhraoui.smartclaims.ui.validationsinistre

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaims.ui.sinistre.SinistreViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidationAdapter @Inject constructor() : RecyclerView.Adapter<ValidationHolder>() {
    var list: List<ValidationModel> = listOf()
    lateinit var viewModel: SinistreViewModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValidationHolder {
        return ValidationHolder(parent)
    }

    override fun onBindViewHolder(holder: ValidationHolder, position: Int) {
        holder.bind(list[position])



        holder.itemView.setOnClickListener {
            when (position) {
                (0) -> {
                    viewModel.saveType(holder.itemView.context, "collision")

                }

                (1) -> {
                    //  viewModel.saveType(holder.itemView.context,"sinistre")

                }


            }


        }
    }

    fun setItem(list: List<ValidationModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}
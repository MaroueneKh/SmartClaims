package com.marouenekhadhraoui.smartclaims.ui.validationsinistre

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaims.ui.sinistre.SinistreViewModel
import com.marouenekhadhraoui.smartclaims.utils.TO_SIGNIN_OR_SIGNUP
import kotlinx.android.synthetic.main.layout_validation_sinistre.view.*
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

        val bundle = bundleOf(
            "uri1" to list[position].uri1.toString(),
            "uri2" to list[position].uri2.toString(),
            "uri3" to list[position].uri3.toString(),
            "uri4" to list[position].uri4.toString()
        )


        holder.itemView.image1.setOnClickListener {

            startActivity(holder.itemView.context, DiaporamaActivity(), bundle)
        }

        holder.itemView.image2.setOnClickListener {
            startActivity(holder.itemView.context, DiaporamaActivity(), bundle)

        }

        holder.itemView.image3.setOnClickListener {
            startActivity(holder.itemView.context, DiaporamaActivity(), bundle)

        }


        holder.itemView.image4.setOnClickListener {
            startActivity(holder.itemView.context, DiaporamaActivity(), bundle)

        }


    }

    fun setItem(list: List<ValidationModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    fun startActivity(context: Context, activity: Activity, bundle: Bundle) {
        val intent = Intent(context, activity::class.java)
        intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        context.startActivity(intent, bundle)
    }
}
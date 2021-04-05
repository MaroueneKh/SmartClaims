package com.marouenekhadhraoui.smartclaims.ui.home

import android.os.Bundle
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaims.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeAdapter @Inject constructor() : RecyclerView.Adapter<HomeHolder>() {
    var list: List<HomeModel> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(parent)
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.bind(list[position])
        val b = Bundle()
        val navDirections: NavDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return b
            }

            override fun getActionId(): Int {

                return R.id.action_navigation_home_to_sinistreFragment
            }


        }
        holder.itemView.setOnClickListener {
            Navigation.findNavController(it!!).navigate(navDirections)
        }
    }

    fun setItem(list: List<HomeModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}
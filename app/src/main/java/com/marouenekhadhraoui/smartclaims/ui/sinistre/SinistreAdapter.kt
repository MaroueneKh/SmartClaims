package com.marouenekhadhraoui.smartclaims.ui.sinistre


import android.os.Bundle
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaims.R
import javax.inject.Singleton

@Singleton
class SinistreAdapter : RecyclerView.Adapter<SinistreHolder>() {
    var list: List<SinistreModel> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SinistreHolder {
        return SinistreHolder(parent)
    }

    override fun onBindViewHolder(holder: SinistreHolder, position: Int) {
        holder.bind(list[position])
        val b = Bundle()
        val navDirections: NavDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return b
            }

            override fun getActionId(): Int {

                return R.id.action_navigation_home_to_settingsFragment
            }


        }
        holder.itemView.setOnClickListener {
            Navigation.findNavController(it!!).navigate(navDirections)
        }
    }

    fun setItem(list: List<SinistreModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}
package com.marouenekhadhraoui.smartclaims.ui.sinistre


import android.os.Bundle
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaims.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SinistreAdapter @Inject constructor() : RecyclerView.Adapter<SinistreHolder>() {
    var list: List<SinistreModel> = listOf()
    lateinit var viewModel: SinistreViewModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SinistreHolder {
        return SinistreHolder(parent)
    }

    override fun onBindViewHolder(holder: SinistreHolder, position: Int) {
        holder.bind(list[position])

        val bundle = bundleOf("position" to position)
        val navDirections: NavDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_sinistreFragment_to_declarationFragment
            }
        }
        holder.itemView.setOnClickListener {
            when (position) {
                (0) -> {
                    viewModel.saveType(holder.itemView.context, "collision")
                    Navigation.findNavController(it!!).navigate(navDirections)
                }

                (1) -> {
                    //  viewModel.saveType(holder.itemView.context,"sinistre")

                }


            }


        }
    }

    fun setItem(list: List<SinistreModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}
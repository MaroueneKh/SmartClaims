package com.marouenekhadhraoui.smartclaims.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaims.ui.countdown.CountDownActivity
import com.marouenekhadhraoui.smartclaims.utils.TO_SIGNIN_OR_SIGNUP
import kotlinx.android.synthetic.main.layout_dossier_item.view.*
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
        holder.itemView.img_logo9.setOnClickListener { view ->
            startActivity(CountDownActivity(), view, list[position].date)
        }

    }

    fun setItem(list: List<DossierModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    fun startActivity(activity: Activity, view: View, date: String) {
        val intent = Intent(view.context, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        intent.putExtra("date", date)
        view.context.startActivity(intent)
    }
}
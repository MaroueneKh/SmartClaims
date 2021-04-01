package com.marouenekhadhraoui.smartclaims.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var adapter: HomeAdapter

    var list: List<HomeModel> = listOf()
    var logger: Logger = Logger()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)


        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HomeAdapter()
        val list = listOf(
            HomeModel(R.drawable.ic_overturned_car, "Sinistre"),
            HomeModel(R.drawable.ic_tow_truck, "Remorquage"),
            HomeModel(R.drawable.ic_rent_a_car, "Location")
        )
        // startAnimation()
        adapter.setItem(list)
        viewPager2.adapter = adapter
        dots_indicator.setViewPager2(viewPager2)
    }


}
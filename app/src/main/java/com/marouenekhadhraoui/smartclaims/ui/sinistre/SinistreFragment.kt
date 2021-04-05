package com.marouenekhadhraoui.smartclaims.ui.sinistre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class SinistreFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var adapter: SinistreAdapter

    var list: List<SinistreModel> = listOf()
    var logger: Logger = Logger()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)


        return inflater.inflate(R.layout.fragment_sinistre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillList()
        setAdapter()
    }

    fun fillList() {
        list = listOf(
            SinistreModel(
                R.drawable.ic_car_collision,
                "Collision",
                "Mon véhicule a été impliqué dans une collision"
            ),
            SinistreModel(R.drawable.ic_autres, "Autres", "Autres types d'accidents"),

            )
    }

    fun setAdapter() {
        // startAnimation()
        adapter.setItem(list)
        viewPager2.adapter = adapter
        dots_indicator.setViewPager2(viewPager2)

    }

}
package com.marouenekhadhraoui.smartclaims.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.FragmentDashboardBinding
import com.marouenekhadhraoui.smartclaims.ui.degats.MarginItemDecoration
import com.marouenekhadhraoui.smartclaims.utils.Status
import com.marouenekhadhraoui.smartclaims.utils.internetErr
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {


    private lateinit var binding: FragmentDashboardBinding


    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    @Inject
    lateinit var adapter: DossierAdapter

    @Inject
    lateinit var logger: Logger

    private lateinit var linearLayoutManager: LinearLayoutManager


    var list: ArrayList<DossierModel> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        binding.lifecycleOwner = this



        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        observeDossiers()


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeDossiers() {
        dashboardViewModel.dossier.observe(requireActivity(), Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    logger.log("success")
                    if (it.data!!.isEmpty()) {
                        logger.log("null")
                    } else {
                        llProgressBar.visibility = View.GONE
                        list = ArrayList(it.data)
                        setAdapter()
                    }

                }
                Status.LOADING -> {
                    logger.log("loading")
                    llProgressBar.visibility = View.VISIBLE

                }
                Status.ERROR -> {
                    logger.log("error")
                    if (it.message.equals(internetErr)) {
                        logger.log("internet error")
                        //  Snackbar.make(findViewById(R.id.constraint), "Verifier votre connexion", Snackbar.LENGTH_LONG).show()
                    }

                }
            }
        })

    }

    fun bindViewModel() {
        binding.viewModel = dashboardViewModel
    }

    fun setAdapter() {
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerViewDashboard.layoutManager = linearLayoutManager
        adapter.setItem(list)
        recyclerViewDashboard.adapter = adapter

        recyclerViewDashboard.addItemDecoration(
            MarginItemDecoration(1)
        )

    }

}
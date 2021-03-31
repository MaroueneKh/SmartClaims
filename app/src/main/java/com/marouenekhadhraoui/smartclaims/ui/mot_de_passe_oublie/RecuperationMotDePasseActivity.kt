package com.marouenekhadhraoui.smartclaims.ui.mot_de_passe_oublie

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle

import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.RecupererPasswordActivityBinding


import com.marouenekhadhraoui.smartclaims.utils.TO_SIGNIN_OR_SIGNUP

import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject


@AndroidEntryPoint
class RecuperationMotDePasseActivity : AppCompatActivity() {
    private lateinit var binding: RecupererPasswordActivityBinding
    private val recupViewModel: RecuperationViewModel by viewModels()

    @Inject
    lateinit var logger: Logger


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupNavigation()
        bindViewModel()
    }


    private fun setupNavigation() {
        recupViewModel.pressBtnParmailEvent.observe(this, Observer {
            it?.let {
                startActivity(EnvoyerRequeteActivity())
            }
        })
        recupViewModel.pressBtnParsmsEvent.observe(this, Observer {
            it?.let {
                startActivity(EnvoyerRequeteSMSActivity())
            }
        })
    }


    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.recuperer_password_activity)
        binding.lifecycleOwner = this
    }

    fun bindViewModel() {
        binding.viewModel = recupViewModel
    }

    fun startActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        startActivity(intent)
        finish()
    }


}
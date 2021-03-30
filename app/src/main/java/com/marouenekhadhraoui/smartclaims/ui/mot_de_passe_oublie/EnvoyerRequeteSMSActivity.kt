package com.marouenekhadhraoui.smartclaims.ui.mot_de_passe_oublie

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R

import com.marouenekhadhraoui.smartclaims.databinding.EnvoyerRequetesmsBinding

import com.marouenekhadhraoui.smartclaims.utils.TO_SIGNIN_OR_SIGNUP

import javax.inject.Inject

class EnvoyerRequeteSMSActivity : AppCompatActivity() {
    private lateinit var binding: EnvoyerRequetesmsBinding
  //  private val envoyerRequeteViewModel: EnvoyerRequeteViewModel by viewModels()
    @Inject
    lateinit var logger: Logger


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
       // bindViewModel()
    }







    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.envoyer_requetesms)
        binding.lifecycleOwner = this
    }

    //fun bindViewModel() {
       // binding.viewModel = envoyerRequeteViewModel
   // }

    fun startActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        startActivity(intent)
        finish()
    }





}
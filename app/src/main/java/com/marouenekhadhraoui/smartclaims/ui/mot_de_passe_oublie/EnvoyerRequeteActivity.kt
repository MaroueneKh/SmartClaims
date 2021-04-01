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
import com.google.android.material.snackbar.Snackbar
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.EnvoyerRequeteBinding

import com.marouenekhadhraoui.smartclaims.ui.signin.SignInActivity
import com.marouenekhadhraoui.smartclaims.utils.Status

import com.marouenekhadhraoui.smartclaims.utils.TO_SIGNIN_OR_SIGNUP
import com.marouenekhadhraoui.smartclaims.utils.internetErr
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EnvoyerRequeteActivity : AppCompatActivity() {
    private lateinit var binding: EnvoyerRequeteBinding
    private val envoyerRequeteViewModel: EnvoyerRequeteViewModel by viewModels()

    @Inject
    lateinit var logger: Logger


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupNavigation()
        bindViewModel()
        setupsignup()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeAssure() {
        envoyerRequeteViewModel.request.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    logger.log("success")
                    if (it.data!!.isEmpty()) {
                        logger.log("null")
                        Snackbar.make(
                            findViewById(R.id.constraint),
                            "Verifier votre mail",
                            Snackbar.LENGTH_LONG
                        ).show()
                    } else {
                        logger.log("not null")
                        suspend { Snackbar.make(findViewById(R.id.constraint), "Email sent", Snackbar.LENGTH_LONG).show() }
                        startActivity(SignInActivity())
                    }

                }
                Status.LOADING -> {
                    logger.log("loading")
                }
                Status.ERROR -> {
                    logger.log("error")
                    if (it.message.equals(internetErr)) {
                        logger.log("internet error")
                        Snackbar.make(findViewById(R.id.constraint), "Verifier votre connexion", Snackbar.LENGTH_LONG).show()
                    }

                }
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupsignup() {
        envoyerRequeteViewModel.pressBtnRequestEvent.observe(this, Observer {
            observeAssure()
        })
    }


    private fun setupNavigation() {

    }


    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.envoyer_requete)
        binding.lifecycleOwner = this
    }

    fun bindViewModel() {
        binding.viewModel = envoyerRequeteViewModel
    }

    fun startActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        startActivity(intent)
        finish()
    }


}
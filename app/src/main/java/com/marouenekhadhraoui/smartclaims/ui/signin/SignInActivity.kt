package com.marouenekhadhraoui.smartclaims.ui.signin

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
import com.marouenekhadhraoui.smartclaims.ui.main.MainActivity
import com.marouenekhadhraoui.smartclaims.ui.mot_de_passe_oublie.RecuperationMotDePasseActivity
import com.marouenekhadhraoui.smartclaims.ui.signup.SignUPActivity
import com.marouenekhadhraoui.smartclaims.utils.Status
import com.marouenekhadhraoui.smartclaims.utils.TO_SIGNIN_OR_SIGNUP
import com.marouenekhadhraoui.smartclaims.utils.fadeTo
import com.marouenekhadhraoui.smartclaims.utils.internetErr
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.signin_activity.*
import javax.inject.Inject


@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private lateinit var binding: com.marouenekhadhraoui.smartclaims.databinding.SigninActivityBinding
    private val signInViewModel: SignInViewModel by viewModels()

    @Inject
    lateinit var logger: Logger


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupNavigation()
        setuplogin()
        bindViewModel()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setuplogin() {
        signInViewModel.pressBtnSeConnecterEvent.observe(this, Observer {
            observeAssure()
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeAssure() {
        signInViewModel.assure.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    logger.log("success")
                    if (it.data!!.isEmpty()) {
                        logger.log("null")
                        warning.fadeTo(true)
                    } else {
                        signInViewModel.saveToken(this, it.data[0].token)
                        logger.log("not null")
                        warning.fadeTo(false)
                        startActivity(MainActivity())
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

    private fun setupNavigation() {
        signInViewModel.pressBtnSinscrireEvent.observe(this, Observer {
            it?.let {
                startActivity(SignUPActivity())
            }
        })

        signInViewModel.ressLinkoublieEvent.observe(this, Observer {
            it?.let {
                startActivity(RecuperationMotDePasseActivity())
            }
        })
    }


    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.signin_activity)
        binding.lifecycleOwner = this
    }

    fun bindViewModel() {
        binding.viewModel = signInViewModel
    }

    fun startActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        startActivity(intent)
        finish()
    }

}
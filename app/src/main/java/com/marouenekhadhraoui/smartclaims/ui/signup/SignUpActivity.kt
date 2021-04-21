package com.marouenekhadhraoui.smartclaims.ui.signup

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.SignupActivityBinding
import com.marouenekhadhraoui.smartclaims.ui.signin.SignInActivity
import com.marouenekhadhraoui.smartclaims.utils.Status
import com.marouenekhadhraoui.smartclaims.utils.TO_SIGNIN_OR_SIGNUP
import com.marouenekhadhraoui.smartclaims.utils.internetErr
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.signup_activity.*
import javax.inject.Inject

@AndroidEntryPoint
class SignUPActivity : AppCompatActivity() {
    private val signUpViewModel: SignUpViewModel by viewModels()
    private lateinit var binding: SignupActivityBinding

    @Inject
    lateinit var logger: Logger

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupNavigation()
        bindViewModel()
        setSpinner()
        setupsignup()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeAssure() {
        signUpViewModel.newassure.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    logger.log("success")
                    if (it.data!!.isEmpty()) {
                        logger.log("null")
                        Snackbar.make(findViewById(R.id.constraint), "Inscription failed", Snackbar.LENGTH_LONG).show()
                    } else {
                        logger.log(it.data[0].message)
                        signUpViewModel.saveToken(this, it.data[0].message)
                        logger.log("not null")
                        Snackbar.make(
                            findViewById(R.id.constraint),
                            "Compté crée avec succés",
                            Snackbar.LENGTH_LONG
                        ).show()
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
        signUpViewModel.pressBtnSinscrireEvent.observe(this, Observer {
            observeAssure()
        })
    }

    private fun setupNavigation() {
        signUpViewModel.pressBtnSeconnecterEvent.observe(this, Observer {
            it?.let {
                startActivity(SignInActivity())
            }
        })
    }

    private fun setSpinner() {

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.agences_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            agances_spinner.adapter = adapter
        }
    }

    fun startActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        startActivity(intent)
        finish()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.signup_activity)
        binding.lifecycleOwner = this
    }

    fun bindViewModel() {
        binding.viewModel = signUpViewModel
    }

}
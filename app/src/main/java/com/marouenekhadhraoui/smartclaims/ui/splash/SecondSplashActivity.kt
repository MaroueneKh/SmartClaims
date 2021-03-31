package com.marouenekhadhraoui.smartclaims.ui.splash


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.SecondSplashActivityBinding
import com.marouenekhadhraoui.smartclaims.ui.main.MainActivity
import com.marouenekhadhraoui.smartclaims.ui.signin.SignInActivity
import com.marouenekhadhraoui.smartclaims.utils.TO_SIGNIN_OR_SIGNUP
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SecondSplashActivity : AppCompatActivity() {
    private lateinit var binding: SecondSplashActivityBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        bindViewModel()
        subscribeToData()
        setContentView(R.layout.second_splash_activity)

    }

    private fun subscribeToData() {
        val connObserver = Observer<Boolean> { connected ->
            // Update the UI, in this case, a TextView.
            if (connected)
                startActivity(MainActivity())
            else
                startActivity(SignInActivity())
        }
        splashViewModel.isConnected.observe(this, connObserver)
        //   splashViewModel.isConnected.observe(this) { connected ->
        //     if (connected)
        //       startActivity(MainActivity())
        // else
        //   startActivity(SignInActivity())
        // }
        val darkObserver = Observer<Boolean> { darkmode ->
            // Update the UI, in this case, a TextView.
            if (darkmode)
                startActivity(MainActivity())
            else
                startActivity(SignInActivity())
        }
        splashViewModel.isdarkenabled.observe(this, darkObserver)

        /*  splashViewModel.isdarkenabled.observe(this) { darkmode ->
              val defaultMode = if (darkmode) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }

                AppCompatDelegate.setDefaultNightMode(defaultMode)

            }
          */
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.second_splash_activity)
        binding.lifecycleOwner = this
    }

    fun bindViewModel() {
        binding.viewModelSplash = splashViewModel
    }

    fun startActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        startActivity(intent)
        finish()
    }


}
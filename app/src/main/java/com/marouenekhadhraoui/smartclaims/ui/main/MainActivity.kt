package com.marouenekhadhraoui.smartclaims.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marouenekhadhraoui.smartclaims.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navController = findNavController(R.id.nav_host_fragment)



        navView.setupWithNavController(navController)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val childFragments = navHostFragment?.childFragmentManager?.fragments

        childFragments?.forEach {
            val navHostFragment = it.childFragmentManager.findFragmentById(R.id.nav_host_fragmentSinistre)
            val childFragments = navHostFragment?.childFragmentManager?.fragments
            childFragments?.forEach {
                it.onActivityResult(22, resultCode, data)
            }
        }


    }


}
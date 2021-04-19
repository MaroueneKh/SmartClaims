package com.marouenekhadhraoui.smartclaims.ui.validationsinistre

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.ActivityDiaporamaBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_diaporama.*
import javax.inject.Inject


@AndroidEntryPoint
class DiaporamaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiaporamaBinding
    var list: List<DiaporamaModel> = listOf()


    @Inject
    lateinit var logger: Logger


    @Inject
    lateinit var adapter: DiaporamaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        fillList()
        setAdapter()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diaporama)
        binding.lifecycleOwner = this
    }

    private fun fillList() {
        val extras = intent.extras
        val uri1 = extras!!.get("uri1")
        val uri2 = extras.get("uri2")
        val uri3 = extras.get("uri3")
        val uri4 = extras.get("uri4")

        list = listOf(
            DiaporamaModel(
                uri1.toString().toUri(),
            ),
            DiaporamaModel(
                uri2.toString().toUri(),
            ),
            DiaporamaModel(
                uri3.toString().toUri(),
            ),
            DiaporamaModel(
                uri4.toString().toUri()
            ),
        )
    }

    private fun setAdapter() {
        adapter.setItem(list)
        viewPager2Diaporama.adapter = adapter
    }

}
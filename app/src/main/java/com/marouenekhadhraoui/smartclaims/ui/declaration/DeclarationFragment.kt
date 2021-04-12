package com.marouenekhadhraoui.smartclaims.ui.declaration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.FragmentDeclarationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_declaration.*
import javax.inject.Inject

@AndroidEntryPoint
class DeclarationFragment : Fragment() {
    @Inject
    lateinit var logger: Logger
    private val viewModel: DeclarationViewModel by activityViewModels()
    private lateinit var binding: FragmentDeclarationBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_declaration, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        manageStepper()
    }

    fun setButton1checked() {

        btn1.setBackgroundResource(R.drawable.step_button_checked)
    }

    fun setButton1done() {
        divider3.setBackgroundColor(resources.getColor(R.color.mainblue))
        btn1.setBackgroundResource(R.drawable.step_button_done)
        btn1.text = ""
    }

    fun setButton2checked() {

        btn2.setBackgroundResource(R.drawable.step_button_checked)
    }

    fun setButton2done() {
        divider4.setBackgroundColor(resources.getColor(R.color.mainblue))

        btn2.setBackgroundResource(R.drawable.step_button_done)
        btn2.text = ""
    }

    fun setButton3checked() {

        btn5.setBackgroundResource(R.drawable.step_button_checked)
    }

    fun setButton3done() {
        divider5.setBackgroundColor(resources.getColor(R.color.mainblue))

        btn5.setBackgroundResource(R.drawable.step_button_done)
        btn5.text = ""
    }

    fun setButton4checked() {

        btn6.setBackgroundResource(R.drawable.step_button_checked)
    }

    fun manageStepper() {
        logger.log("manage stepper")
        viewModel.stateButton1.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    it?.let {
                        when (it) {
                            "checked" -> setButton1checked()
                            "done" -> setButton1done()
                        }
                    }

                })

        viewModel.stateButton2.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    it?.let {
                        when (it) {
                            "checked" -> setButton2checked()
                            "done" -> setButton2done()
                        }
                    }

                })
        viewModel.stateButton3.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    it?.let {
                        when (it) {
                            "checked" -> setButton3checked()
                            "done" -> setButton3done()

                        }

                    }

                })
        viewModel.stateButton4.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    it?.let {
                        when (it) {
                            "checked" -> setButton4checked()


                        }

                    }

                })

    }

    fun bindViewModel() {
        binding.viewModel = viewModel
    }


}
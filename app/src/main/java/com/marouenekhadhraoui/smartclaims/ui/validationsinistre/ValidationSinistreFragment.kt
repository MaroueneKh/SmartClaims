package com.marouenekhadhraoui.smartclaims.ui.validationsinistre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.FragmentValidationSinistreBinding
import com.marouenekhadhraoui.smartclaims.ui.declaration.DeclarationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_validation_sinistre.*
import javax.inject.Inject

@AndroidEntryPoint
class ValidationSinistreFragment : Fragment() {


    private lateinit var binding: FragmentValidationSinistreBinding

    private val viewModel: ValidationSinistreViewModel by activityViewModels()

    private val viewModelDeclaration: DeclarationViewModel by activityViewModels()

    var list: List<ValidationModel> = listOf()


    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var adapter: ValidationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_validation_sinistre,
            container,
            false
        )
        binding.lifecycleOwner = this



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModel()
        viewModelDeclaration.stateButton4.postValue("done")
        viewModel.getScan1()
        viewModel.getScan2()
        viewModel.getVid1()
        viewModel.getVid2()
        viewModel.getDegats1()
        viewModel.getDegats2()
        viewModel.getDegats3()
        viewModel.getDegats4()

        logger.log(viewModel.list1.size.toString())



        fillList()
        setAdapter()

        //   pressGalleryInFragment()
        // pressScanButton()
        //pressSuivantButton()


    }


    fun fillList() {

        list = listOf(
            ValidationModel(
                viewModel.list1[0],
                viewModel.list1[1],
                viewModel.list1[2],
                viewModel.list1[3]
            ),
            ValidationModel(
                viewModel.list2[0],
                viewModel.list2[1],
                viewModel.list2[2],
                viewModel.list2[3]
            ),
        )
    }


    fun bindViewModel() {
        binding.viewModel = viewModel
    }

    fun setAdapter() {
        adapter.setItem(list)
        viewPager2Validation.adapter = adapter
        dots_indicatorValidation.setViewPager2(viewPager2Validation)
    }


}
package com.marouenekhadhraoui.smartclaims.ui.validationsinistre

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.work.*
import com.google.android.material.snackbar.Snackbar
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.FragmentValidationSinistreBinding
import com.marouenekhadhraoui.smartclaims.ui.declaration.DeclarationViewModel
import com.marouenekhadhraoui.smartclaims.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_validation_sinistre.*
import kotlinx.android.synthetic.main.signin_activity.*
import javax.inject.Inject

@AndroidEntryPoint
class ValidationSinistreFragment : Fragment() {


    private lateinit var binding: FragmentValidationSinistreBinding

    private val viewModel: ValidationSinistreViewModel by activityViewModels()

    private val viewModelDeclaration: DeclarationViewModel by activityViewModels()

    var list: List<ValidationModel> = listOf()

    private lateinit var navDirections: NavDirections


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

    @RequiresApi(Build.VERSION_CODES.M)
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
        viewModel.gettype()
        viewModel.getlang()
        viewModel.getlat()

        logger.log(viewModel.list1.size.toString())



        fillList()
        setAdapter()
        pressSuivantButton()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun pressSuivantButton() {

        viewModel.pressBtnSuivantEvent.observe(viewLifecycleOwner, Observer {
            logger.log("upload en cours")
            llProgressBar.visibility = View.VISIBLE
            viewModel.getIdNewDossier()
            viewModel.idNewDossier.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        logger.log("success")
                        if (it.data!!.isEmpty()) {
                            logger.log("null")
                            warning.fadeTo(true)
                        } else {
                            logger.log("not null")
                            logger.log(it.data[0].message)
                            val myData: Data = workDataOf(
                                    SCAN1 to viewModel.list1[0],
                                    SCAN2 to viewModel.list1[1],
                                    VID1 to viewModel.list1[2],
                                    VID2 to viewModel.list1[3],
                                    DEGAT1 to viewModel.list2[0],
                                    DEGAT2 to viewModel.list2[1],
                                    DEGAT3 to viewModel.list2[2],
                                    DEGAT4 to viewModel.list2[3],
                                    ID to it.data[0].message)
                            val constraints = Constraints.Builder()
                                    .setRequiredNetworkType(NetworkType.CONNECTED)
                                    .build()
                            val uploadWork = OneTimeWorkRequestBuilder<UploadFilesWorker>()
                                    .setInputData(myData)

                                    .setConstraints(constraints)

                                    .build()
                            WorkManager.getInstance(requireContext()).enqueue(uploadWork)
                            setNavDirections()
                            Navigation.findNavController(requireView()).navigate(navDirections)
                        }

                    }
                    Status.LOADING -> {
                        logger.log("loading")
                    }
                    Status.ERROR -> {
                        logger.log("error")
                        if (it.message.equals(internetErr)) {
                            logger.log("internet error")
                            Snackbar.make(requireActivity().findViewById(R.id.constraint), "Verifier votre connexion", Snackbar.LENGTH_LONG).show()
                        }

                    }
                }
            })


        })

    }

    fun setNavDirections() {

        val bundle = bundleOf("lat" to "heheeh", "long" to "dd")

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_validationSinistreFragment_to_dashboardFragment
            }
        }
    }


    fun fillList() {

        list = listOf(
                ValidationModel(
                        viewModel.list1[0].toUri(),
                        viewModel.list1[1].toUri(),
                        viewModel.list1[2].toUri(),
                        viewModel.list1[3].toUri()
                ),
            ValidationModel(
                    viewModel.list2[0].toUri(),
                    viewModel.list2[1].toUri(),
                    viewModel.list2[2].toUri(),
                    viewModel.list2[3].toUri()
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
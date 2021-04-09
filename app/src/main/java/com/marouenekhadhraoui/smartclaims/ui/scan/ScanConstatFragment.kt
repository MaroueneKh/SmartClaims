package com.marouenekhadhraoui.smartclaims.ui.scan


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.FragmentScanConstatBinding
import com.marouenekhadhraoui.smartclaims.ui.declaration.DeclarationViewModel
import com.marouenekhadhraoui.smartclaims.utils.fadeTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_scan_constat.*
import javax.inject.Inject


@AndroidEntryPoint
class ScanConstatFragment : Fragment() {


    private lateinit var binding: FragmentScanConstatBinding

    private val viewModel: ScanConstatViewModel by activityViewModels()

    private val viewModelDeclaration: DeclarationViewModel by activityViewModels()

    private val LOCATION_PERMISSION_REQUEST = 102

    var i: Int = 0


    @Inject
    lateinit var logger: Logger


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //instasiate getfused location with context

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scan_constat, container, false)
        binding.lifecycleOwner = this



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModel()
        viewModelDeclaration.stateButton1.postValue("done")
        viewModelDeclaration.stateButton2.postValue("checked")
        img_scan1.fadeTo(false)
        logger.log("button 1 in scan : " + viewModelDeclaration.stateButton1.value.toString())
        pressScanButton()

        pressGalleryInFragment()


    }

    private fun pressGalleryInFragment() {
        viewModel.pressGaleryEvent.observe(viewLifecycleOwner, Observer {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //requests are not permitted
                requestPermission()
            } else {

                pickImageFromGallery()
            }


        })
    }

    private fun pressScanButton() {
        viewModel.pressBtnScanEvent.observe(viewLifecycleOwner, Observer {
            parentFragmentManager.let {
                OptionsBottomSheetFragment.newInstance(Bundle()).apply {
                    show(it, tag)
                }
            }

        })
    }

    fun bindViewModel() {
        binding.viewModel = viewModel
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    showAlertGallery()

                }
            }
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001

        @JvmStatic
        fun newInstance(): ScanConstatFragment {

            return ScanConstatFragment()
        }
    }


    fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), LOCATION_PERMISSION_REQUEST)

    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        logger.log("in result")
        logger.log(requestCode.toString())

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            logger.log("from gallery")
            i++

            img_logo2.fadeTo(false)
            textView3.fadeTo(false)
            textView4.fadeTo(false)
            img_scan1.fadeTo(true)
            when (i) {

                1 -> {
                    img_scan1.fadeTo(true)

                    img_scan1.setImageURI(data?.data)

                }
                2 -> {
                    img_scan2.fadeTo(true)
                    img_scan2.setImageURI(data?.data)
                }
            }


        } else if (resultCode == Activity.RESULT_OK && requestCode == 22) {
            logger.log("from gallery")
            i++

            img_logo2.fadeTo(false)
            textView3.fadeTo(false)
            textView4.fadeTo(false)
            img_scan1.fadeTo(true)
            when (i) {

                1 -> {
                    img_scan1.fadeTo(true)

                    img_scan1.setImageURI(data?.extras?.get("ActivityResult") as Uri?)

                }
                2 -> {
                    img_scan2.fadeTo(true)
                    img_scan2.setImageURI(data?.extras?.get("ActivityResult") as Uri?)
                }
            }

        }
    }

    private fun showAlertGallery() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage("Veuillez accepter la permission ")
        dialog.setPositiveButton("Settings") { _, _ ->
            val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(myIntent)
        }
        dialog.setNegativeButton("Cancel") { _, _ ->
        }
        dialog.setCancelable(false)
        dialog.show()
    }


}
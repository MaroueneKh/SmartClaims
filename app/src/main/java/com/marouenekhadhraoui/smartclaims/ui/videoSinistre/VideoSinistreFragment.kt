package com.marouenekhadhraoui.smartclaims.ui.videoSinistre

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.FragmentVideoSinistreBinding
import com.marouenekhadhraoui.smartclaims.ui.declaration.DeclarationViewModel
import com.marouenekhadhraoui.smartclaims.utils.fadeTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_scan_constat.*
import kotlinx.android.synthetic.main.fragment_scan_constat.img_logo2
import kotlinx.android.synthetic.main.fragment_scan_constat.textView3
import kotlinx.android.synthetic.main.fragment_scan_constat.textView4
import kotlinx.android.synthetic.main.fragment_video_sinistre.*
import kotlinx.android.synthetic.main.fragment_video_sinistre.btnNon
import kotlinx.android.synthetic.main.fragment_video_sinistre.btnSuivant
import javax.inject.Inject

@AndroidEntryPoint
class VideoSinistreFragment : Fragment() {


    private lateinit var binding: FragmentVideoSinistreBinding

    private val viewModel: VideoSinistreViewModel by activityViewModels()

    private val viewModelDeclaration: DeclarationViewModel by activityViewModels()

    private val LOCATION_PERMISSION_REQUEST = 102

    var i: Int = 0
    private lateinit var navDirections: NavDirections

    lateinit var uri1: Uri
    lateinit var uri2: Uri


    @Inject
    lateinit var logger: Logger


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //instasiate getfused location with context

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_sinistre, container, false)
        binding.lifecycleOwner = this



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle
    ?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        viewModelDeclaration.stateButton2.postValue("done")
        viewModelDeclaration.stateButton3.postValue("checked")

        btnSuivant.visibility = View.GONE
        btnSuivant.isClickable = false
        pressScanButton()

        pressGalleryInFragment()
        pressSuivantButton()


    }

    private fun pressGalleryInFragment() {
        viewModel.pressGaleryEvent.observe(viewLifecycleOwner, Observer {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //requests are not permitted
                requestPermission()
            } else {

                pickVideoFromGallery()
            }


        })
        viewModel.pressCameraEvent.observe(viewLifecycleOwner, Observer {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //requests are not permitted
                requestPermission()
            } else {

                pickVideoFromCamera()
            }


        })
    }

    private fun pressScanButton() {
        viewModel.pressBtnTakeVideoEvent.observe(viewLifecycleOwner, Observer {

            parentFragmentManager.let {
                OptionsBottomSheetVideoFragment.newInstance(Bundle()).apply {
                    show(it, tag)
                }
            }

        })
    }

    fun setNavDirections() {

        val bundle = bundleOf("lat" to "heheeh", "long" to "dd")

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_videoSinistreFragment_to_degatsFragment
            }
        }
    }

    private fun pressSuivantButton() {
        setNavDirections()
        viewModel.pressBtnSuivantEvent.observe(viewLifecycleOwner, Observer {
            viewModel.saveVideo(requireContext(), uri1.toString(), uri2.toString())
            Navigation.findNavController(requireView()).navigate(navDirections)

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
                    pickVideoFromGallery()
                } else {
                    showAlertGallery()

                }
            }
        }
    }

    private fun pickVideoFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "video/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun pickVideoFromCamera() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
            }
        }

    }


    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001

        private val REQUEST_VIDEO_CAPTURE = 1

        @JvmStatic
        fun newInstance(): VideoSinistreFragment {

            return VideoSinistreFragment()
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
            vid_scan1.fadeTo(true)

            when (i) {
                1 -> {
                    vid_scan1.fadeTo(true)
                    uri1 = data?.data!!
                    Glide.with(requireContext()).load(uri1).thumbnail(0.1f).into(vid_scan1)
                }
                2 -> {
                    vid_scan2.fadeTo(true)
                    uri2 = data?.data!!
                    Glide.with(requireContext()).load(uri2).thumbnail(0.1f).into(vid_scan2)
                    btnNon.visibility = View.GONE
                    btnNon.isClickable = false
                    btnSuivant.visibility = View.VISIBLE
                    btnSuivant.isClickable = true
                    setNavDirections()
                }
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_VIDEO_CAPTURE) {
            logger.log("from gallery")
            i++
            img_logo2.fadeTo(false)
            textView3.fadeTo(false)
            textView4.fadeTo(false)
            when (i) {

                1 -> {
                    uri1 = data?.data!!
                    Glide.with(requireContext()).load(uri1).thumbnail(0.1f).into(vid_scan1)
                    vid_scan1.fadeTo(true)
                }
                2 -> {

                    uri2 = data?.data!!
                    Glide.with(requireContext()).load(uri2).thumbnail(0.1f).into(vid_scan2)
                    logger.log(uri2.toString())
                    vid_scan2.fadeTo(true)
                    btnNon.visibility = View.GONE
                    btnNon.isClickable = false
                    btnSuivant.visibility = View.VISIBLE
                    btnSuivant.isClickable = true
                    setNavDirections()

                }
            }
        }
    }

    private fun showPagerDialog() {
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
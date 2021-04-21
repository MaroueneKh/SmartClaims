package com.marouenekhadhraoui.smartclaims.ui.degats

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
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.FragmentDegatsBinding
import com.marouenekhadhraoui.smartclaims.ui.declaration.DeclarationViewModel
import com.marouenekhadhraoui.smartclaims.ui.scan.OptionsBottomSheetFragment
import com.marouenekhadhraoui.smartclaims.ui.scan.ScanConstatFragment
import com.marouenekhadhraoui.smartclaims.utils.fadeTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_degats.*
import kotlinx.android.synthetic.main.fragment_scan_constat.btnNon
import kotlinx.android.synthetic.main.fragment_scan_constat.btnSuivant
import kotlinx.android.synthetic.main.fragment_scan_constat.img_logo2
import kotlinx.android.synthetic.main.fragment_scan_constat.textView3
import kotlinx.android.synthetic.main.fragment_scan_constat.textView4
import javax.inject.Inject


@AndroidEntryPoint
class DegatsFragment : Fragment() {


    private lateinit var binding: FragmentDegatsBinding

    private val viewModel: DegatsViewModel by activityViewModels()

    private val viewModelDeclaration: DeclarationViewModel by activityViewModels()

    private val LOCATION_PERMISSION_REQUEST = 102


    var i: Int = 0

    private lateinit var navDirections: NavDirections

    private lateinit var gridLayoutManager: GridLayoutManager

    @Inject
    lateinit var adapter: DegatsAdapter

    var list: ArrayList<DegatsModel> = ArrayList()

    lateinit var uri1: Uri
    lateinit var uri2: Uri
    lateinit var uri3: Uri
    lateinit var uri4: Uri


    @Inject
    lateinit var logger: Logger


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_degats, container, false)
        binding.lifecycleOwner = this



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModel()
        viewModelDeclaration.stateButton3.postValue("done")
        viewModelDeclaration.stateButton4.postValue("checked")
        logger.log("button 1 in scan : " + viewModelDeclaration.stateButton1.value.toString())
        btnSuivant.visibility = View.GONE
        btnSuivant.isClickable = false

        pressGalleryInFragment()
        pressScanButton()
        pressSuivantButton()


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

    fun setNavDirections() {

        val bundle = bundleOf("lat" to "heheeh", "long" to "dd")

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_degatsFragment_to_validationSinistreFragment
            }
        }
    }


    private fun pressSuivantButton() {
        setNavDirections()

        viewModel.pressBtnSuivantEvent.observe(viewLifecycleOwner, Observer {
            viewModel.saveDegats(
                requireContext(),
                uri1.toString(),
                uri2.toString(),
                uri3.toString(),
                uri4.toString()
            )
            Navigation.findNavController(requireView()).navigate(navDirections)

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

    fun fillList(uri: Uri) {

        list.add(DegatsModel(uri))
    }

    fun setAdapter() {
        gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager
        adapter.setItem(list)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
                MarginItemDecoration(10)
        )

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
            recyclerView.fadeTo(true)
            when (i) {

                (1) -> {
                    if (data != null) {
                        fillList(data.data!!)
                        uri1 = data.data!!
                        setAdapter()
                    }

                }
                (2) -> {
                    if (data != null) {
                        fillList(data.data!!)
                        logger.log("urié" + data.data!!)
                        uri2 = data.data!!
                        setAdapter()
                    }

                }
                (3) -> {
                    if (data != null) {
                        fillList(data.data!!)
                        logger.log("uri3" + data.data!!)
                        uri3 = data.data!!
                        setAdapter()
                    }

                }
                (4) -> {
                    if (data != null) {
                        fillList(data.data!!)
                        uri4 = data.data!!
                        logger.log("uri4" + data.data!!)
                        btnNon.visibility = View.GONE
                        btnNon.isClickable = false
                        btnSuivant.visibility = View.VISIBLE
                        btnSuivant.isClickable = true
                        setAdapter()
                        setNavDirections()
                    }

                }
            }


        } else if (resultCode == Activity.RESULT_OK && requestCode == 22) {
            logger.log("from gallery")
            i++
            recyclerView.fadeTo(true)
            img_logo2.fadeTo(false)
            textView3.fadeTo(false)
            textView4.fadeTo(false)
            when (i) {
                (1) -> {
                    if (data != null) {
                        fillList(data.extras!!.get("ActivityResult") as Uri)
                        uri1 = data.extras!!.get("ActivityResult") as Uri
                        setAdapter()
                    }
                }
                (2) -> {
                    if (data != null) {
                        fillList(data.extras!!.get("ActivityResult") as Uri)
                        uri2 = data.extras!!.get("ActivityResult") as Uri
                        setAdapter()
                    }


                }
                (3) -> {
                    if (data != null) {
                        fillList(data.extras!!.get("ActivityResult") as Uri)
                        uri3 = data.extras!!.get("ActivityResult") as Uri
                        setAdapter()
                    }


                }
                (4) -> {
                    if (data != null) {
                        fillList(data.extras!!.get("ActivityResult") as Uri)
                        uri4 = data.extras!!.get("ActivityResult") as Uri
                        setAdapter()
                        btnNon.visibility = View.GONE
                        btnNon.isClickable = false
                        btnSuivant.visibility = View.VISIBLE
                        btnSuivant.isClickable = true
                        setNavDirections()
                    }

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
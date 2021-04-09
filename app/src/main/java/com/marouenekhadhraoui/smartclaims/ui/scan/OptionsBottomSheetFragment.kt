package com.marouenekhadhraoui.smartclaims.ui.scan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.ui.camera.CameraActivity
import com.marouenekhadhraoui.smartclaims.utils.Event
import com.marouenekhadhraoui.smartclaims.utils.TO_SIGNIN_OR_SIGNUP
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottomsheet.*

@AndroidEntryPoint
class OptionsBottomSheetFragment : BottomSheetDialogFragment() {


    val REQUESTCODE = 102
    private lateinit var logger: Logger


    private val viewModelDeclaration: ScanConstatViewModel by activityViewModels()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        // We can have cross button on the top right corner for providing elemnet to dismiss the bottom sheet
        //iv_close.setOnClickListener { dismissAllowingStateLoss() }
        txt_gallery.setOnClickListener {
            dismissAllowingStateLoss()
            viewModelDeclaration._pressGalleryEvent.value = Event(Unit)


        }
        txt_camera.setOnClickListener {
            this
            dismissAllowingStateLoss()
            val intent = Intent(activity, CameraActivity::class.java)
            startActivityForResult(intent, 22)
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        logger.log(requestCode.toString())

        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            logger.log("from camera")

        }
    }

    fun startActivity(activity: Activity) {
        val intent = Intent(requireContext(), activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): OptionsBottomSheetFragment {
            val fragment = OptionsBottomSheetFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}
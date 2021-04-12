package com.marouenekhadhraoui.smartclaims.ui.videoSinistre

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.utils.Event
import com.marouenekhadhraoui.smartclaims.utils.TO_SIGNIN_OR_SIGNUP
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottomsheet.*

@AndroidEntryPoint
class OptionsBottomSheetVideoFragment : BottomSheetDialogFragment() {


    val REQUESTCODE = 102


    private val viewModelVideo: VideoSinistreViewModel by activityViewModels()
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
            viewModelVideo._pressGalleryEvent.value = Event(Unit)


        }
        txt_camera.setOnClickListener {
            this
            dismissAllowingStateLoss()
            viewModelVideo._pressCameraEvent.value = Event(Unit)
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



        if (resultCode == Activity.RESULT_OK && requestCode == 0) {


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
        fun newInstance(bundle: Bundle): OptionsBottomSheetVideoFragment {
            val fragment = OptionsBottomSheetVideoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}
package com.marouenekhadhraoui.smartclaims.ui.declaration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marouenekhadhraoui.smartclaims.R
import kotlinx.android.synthetic.main.fragment_declaration.*


class DeclarationFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_declaration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonOnSelected()
    }

    fun setButtonOnSelected() {
        btn1.setBackgroundResource(R.drawable.step_button_checked)
    }


}
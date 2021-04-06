package com.marouenekhadhraoui.smartclaims.ui.declaration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError


class StepFragmentSample : Fragment(), Step {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        //initialize your UI
        return inflater.inflate(com.marouenekhadhraoui.smartclaims.R.layout.fragment_declaration, container, false)
    }

    override fun verifyStep(): VerificationError? {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null
    }

    override fun onError(error: VerificationError) {
        TODO("Not yet implemented")
    }

    override fun onSelected() {
        //update UI when selected
    }


}
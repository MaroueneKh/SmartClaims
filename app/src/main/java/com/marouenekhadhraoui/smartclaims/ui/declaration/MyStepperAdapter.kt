package com.marouenekhadhraoui.smartclaims.ui.declaration

import android.content.Context
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel


class MyStepperAdapter(fm: FragmentManager?, context: Context?) :
        AbstractFragmentStepAdapter(fm!!, context!!) {
    override fun createStep(position: Int): Step {
        val step = StepFragmentSample()
        val b = Bundle()
        b.putInt("current position", position)
        step.arguments = b
        return step
    }


    @NonNull
    override fun getViewModel(position: Int): StepViewModel {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return StepViewModel.Builder(context)
                .setTitle("") //can be a CharSequence instead
                .create()
    }

    override fun getCount(): Int {
        return 4
    }
}
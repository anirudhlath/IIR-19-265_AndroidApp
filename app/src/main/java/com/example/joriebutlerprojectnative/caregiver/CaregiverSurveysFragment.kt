/*
 *     Created by Anirudh Lath on 12/16/22, 11:44 AM
 *     anirudhlath@gmail.com
 *     Last modified 12/16/22, 11:43 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.caregiver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.FragmentCaregiverSurveysBinding
import com.example.joriebutlerprojectnative.databinding.FragmentPatientSurveysBinding
import com.example.joriebutlerprojectnative.patient.PatientHomePageFragment
import com.example.joriebutlerprojectnative.surveys.CaregiverBurdenScaleFragment


/**
 * A simple [Fragment] subclass.
 * Use the [CaregiverSurveysFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CaregiverSurveysFragment : Fragment() {
    private var _binding: FragmentCaregiverSurveysBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView2, CaregiverHomePageFragment()).commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCaregiverSurveysBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        binding.buttonCBS.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, CaregiverBurdenScaleFragment()).commit()
        }

        return rootView
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.fade_out)
        }
    }


}
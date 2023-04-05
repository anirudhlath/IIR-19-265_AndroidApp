/*
 *     Created by Anirudh Lath on 4/4/23, 8:40 PM
 *     anirudhlath@gmail.com
 *     Last modified 4/4/23, 8:40 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.caregiver

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.FragmentCaregiverSurveysBinding
import com.example.joriebutlerprojectnative.surveys.AcitivitiesOfDailyLifeScaleFragment
import com.example.joriebutlerprojectnative.surveys.CaregiverBurdenScaleFragment
import com.example.joriebutlerprojectnative.surveys.InstrumentalActivitiesOfDailyLifeScaleFragment
import com.example.joriebutlerprojectnative.surveys.SocialRelationshipIndexFragment


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
        binding.buttonActivitiesOfDailyLifeScale.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, AcitivitiesOfDailyLifeScaleFragment())
                .commit()
        }
        binding.buttonInstrumentalActivitiesOfDailyLifeScale.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainerView2,
                    InstrumentalActivitiesOfDailyLifeScaleFragment()
                ).commit()
        }
        binding.buttonSRI.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, SocialRelationshipIndexFragment()).commit()
        }

        val caregiverSharedPref = requireActivity().getSharedPreferences(
            getString(R.string.caregiverData), Context.MODE_PRIVATE
        )

        checkCompletion(
            caregiverSharedPref,
            "AdlsCompleted",
            binding.buttonActivitiesOfDailyLifeScale
        )
        checkCompletion(caregiverSharedPref, "CBSCompleted", binding.buttonCBS)
        checkCompletion(
            caregiverSharedPref,
            "IADLSCompleted",
            binding.buttonInstrumentalActivitiesOfDailyLifeScale
        )
        checkCompletion(caregiverSharedPref, "SRICompleted", binding.buttonSRI)

        return rootView
    }

    private fun checkCompletion(sharedPref: SharedPreferences, string: String, button: Button) {
        val completed = sharedPref.getInt(string, 0)
        if (completed == 1) {
            button.isEnabled = false
            val img: Drawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_check_24)!!
            button.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
        }
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.fade_out)
        }
    }


}
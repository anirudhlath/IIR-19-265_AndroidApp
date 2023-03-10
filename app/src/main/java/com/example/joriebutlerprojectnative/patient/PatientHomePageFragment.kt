/*
 *     Created by Anirudh Lath on 12/16/22, 11:44 AM
 *     anirudhlath@gmail.com
 *     Last modified 12/16/22, 11:43 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.FragmentPatientHomePageBinding

/**
 * A simple [Fragment] subclass.
 * Use the [PatientHomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientHomePageFragment : Fragment(), OnClickListener {

    private var _binding: FragmentPatientHomePageBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPatientHomePageBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        val imagesButton = binding.imagesButton

        imagesButton.setOnClickListener(this)
        binding.surveysButton.setOnClickListener(this)
        binding.openEndedQuestionsButton.setOnClickListener(this)


        return rootView
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.fade_in)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.fade_out)
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.imagesButton -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, PatientImageSurveyFragment()).commit()

                    return
                }

                R.id.surveysButton -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, PatientSurveysFragment()).commit()

                    return
                }

                R.id.openEndedQuestionsButton -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, PatientOpenEndedQuestionsFragment()).commit()

                    return
                }
            }
        }
    }

}
/*
 *     Created by Anirudh Lath on 4/4/23, 8:40 PM
 *     anirudhlath@gmail.com
 *     Last modified 4/4/23, 8:40 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.surveys

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.caregiver.CaregiverSurveysFragment
import com.example.joriebutlerprojectnative.databinding.QuestionnaireAcitivitiesOfDailyLifeScaleBinding
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


/**
 * A simple [Fragment] subclass.
 * Use the [AcitivitiesOfDailyLifeScaleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AcitivitiesOfDailyLifeScaleFragment : Fragment() {

    private var _binding: QuestionnaireAcitivitiesOfDailyLifeScaleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            QuestionnaireAcitivitiesOfDailyLifeScaleBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        var responses = resources.getStringArray(R.array.Adls_q1_responses_array)
        var arrayAdapter = ArrayAdapter(requireContext(), R.layout.survey_responses, responses)
        binding.q1AdlsMenu.setAdapter(arrayAdapter)
        responses = resources.getStringArray(R.array.Adls_q2_responses_array)
        arrayAdapter = ArrayAdapter(requireContext(), R.layout.survey_responses, responses)
        binding.q2AdlsMenu.setAdapter(arrayAdapter)

        binding.buttonSubmitSurvey.setOnClickListener {
            submitSurvey()
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

    private fun calculateScore(textInputLayout: TextInputLayout): Int {
        when (textInputLayout.editText?.text.toString()[0].toString()) {
            "a" -> {
                return 1
            }
            "b" -> {
                return 0
            }

        }
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun submitSurvey() {
        if(!binding.q1Adls.editText?.text.isNullOrEmpty()
            &&!binding.q2Adls.editText?.text.isNullOrEmpty()
            &&!binding.q3Adls.editText?.text.isNullOrEmpty()
            &&!binding.q4Adls.editText?.text.isNullOrEmpty()
            &&!binding.q5Adls.editText?.text.isNullOrEmpty()
            &&!binding.q6Adls.editText?.text.isNullOrEmpty()
        ) {

            Log.d("ActivityTag", requireActivity()::class.java.simpleName)
            var sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.patientData), Context.MODE_PRIVATE
            )

            if (requireActivity()::class.java.simpleName == "CaregiverActivity") {
                sharedPref = requireActivity().getSharedPreferences(
                    getString(R.string.caregiverData), Context.MODE_PRIVATE
                )
            }


            val score = calculateScore(binding.q1Adls) +
                    calculateScore(binding.q2Adls) +
                    calculateScore(binding.q3Adls) +
                    calculateScore(binding.q4Adls) +
                    calculateScore(binding.q5Adls) +
                    calculateScore(binding.q6Adls)

            val editor = sharedPref.edit()
            editor.putInt("AdlsScore", score)
            editor.putInt("adls_q1", calculateScore(binding.q1Adls))
            editor.putInt("adls_q2", calculateScore(binding.q2Adls))
            editor.putInt("adls_q3", calculateScore(binding.q3Adls))
            editor.putInt("adls_q4", calculateScore(binding.q4Adls))
            editor.putInt("adls_q5", calculateScore(binding.q5Adls))
            editor.putInt("adls_q6", calculateScore(binding.q6Adls))
            editor.putInt("AdlsCompleted", 1)
            editor.apply()

            Log.d("SharedPreferences", "Loading the save data...")
            Log.d(
                "SharedPreferences",
                "Adls Score: " + sharedPref.getInt("AdlsScore", -1).toString()
            )
        }
        else {
            val contextView = requireView()
            Snackbar.make(
                contextView,
                "Please complete all the fields.",
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }

        if (requireActivity()::class.java.simpleName == "CaregiverActivity") {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, CaregiverSurveysFragment()).commit()
        } else {
            parentFragmentManager.beginTransaction()
                .replace(R.id.constraintLayout, PatientSurveysFragment()).commit()
        }

        return

    }


}
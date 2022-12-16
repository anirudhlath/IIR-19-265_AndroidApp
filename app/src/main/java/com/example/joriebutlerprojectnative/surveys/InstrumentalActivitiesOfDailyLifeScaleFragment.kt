/*
 *     Created by Anirudh Lath on 12/16/22, 11:44 AM
 *     anirudhlath@gmail.com
 *     Last modified 12/16/22, 11:43 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.surveys

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.caregiver.CaregiverSurveysFragment
import com.example.joriebutlerprojectnative.databinding.QuestionnaireAcitivitiesOfDailyLifeScaleBinding
import com.example.joriebutlerprojectnative.databinding.QuestionnaireInstrumentalActivitiesOfDailyLifeScaleBinding
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


/**
 * A simple [Fragment] subclass.
 * Use the [InstrumentalActivitiesOfDailyLifeScaleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InstrumentalActivitiesOfDailyLifeScaleFragment : Fragment() {

    private var _binding: QuestionnaireInstrumentalActivitiesOfDailyLifeScaleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.fade_out)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =
            QuestionnaireInstrumentalActivitiesOfDailyLifeScaleBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        binding.buttonSubmitSurvey.setOnClickListener {
            submitSurvey()
        }

        return rootView
    }

    private fun calculateScore(textInputLayout: TextInputLayout): Int {
        if(textInputLayout.editText?.text.toString() ==  "a. Operates telephone on own initiative-looks up and dials numbers, etc."
            || textInputLayout.editText?.text.toString() ==  "b. Dials a few well-known numbers"
            || textInputLayout.editText?.text.toString() ==  "c. Answers telephone but does not dial"
            || textInputLayout.editText?.text.toString() ==  "a. Takes care of all shopping needs independently"
            || textInputLayout.editText?.text.toString() ==  "a. Plans, prepares and serves adequate meals independently"
            || textInputLayout.editText?.text.toString() ==  "a. Maintains house alone or with occasional assistance (e.g. \"heavy work domestic help\")"
            || textInputLayout.editText?.text.toString() ==  "b. Performs light daily tasks such as dish washing, bed making"
            || textInputLayout.editText?.text.toString() ==  "c. Performs light daily tasks but cannot maintain acceptable level of cleanliness"
            || textInputLayout.editText?.text.toString() ==  "d. Needs help with all home maintenance tasks"
            || textInputLayout.editText?.text.toString() ==  "a. Does personal laundry completely"
            || textInputLayout.editText?.text.toString() ==  "b. Launders small items-rinses stockings, etc."
            || textInputLayout.editText?.text.toString() ==  "a. Travels independently on public transportation or drives own car"
            || textInputLayout.editText?.text.toString() ==  "b. Arranges own travel via taxi, but does not otherwise use public transportation"
            || textInputLayout.editText?.text.toString() ==  "c. Travels on public transportation when accompanied by another"
            || textInputLayout.editText?.text.toString() ==  "a. Is responsible for taking medication in correct dosages at correct time"
            || textInputLayout.editText?.text.toString() ==  "a. Manages financial matters independently (budgets, writes checks, pays rent, bills, goes to bank), collects/keeps track of income"
            || textInputLayout.editText?.text.toString() ==  "b. Manages day-to-day purchases, but needs help with banking, major purchases, etc."
        ) {
            return 1
        }
        return 0
    }

    private fun submitSurvey() {
        if(!binding.q1.editText?.text.isNullOrEmpty()
            &&!binding.q2.editText?.text.isNullOrEmpty()
            &&!binding.q3.editText?.text.isNullOrEmpty()
            &&!binding.q4.editText?.text.isNullOrEmpty()
            &&!binding.q5.editText?.text.isNullOrEmpty()
            &&!binding.q6.editText?.text.isNullOrEmpty()
            &&!binding.q7.editText?.text.isNullOrEmpty()
            &&!binding.q8.editText?.text.isNullOrEmpty()
        ) {
            var sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.patientData), Context.MODE_PRIVATE
            )

            if (requireActivity()::class.java.simpleName == "CaregiverActivity") {
                sharedPref = requireActivity().getSharedPreferences(
                    getString(R.string.caregiverData), Context.MODE_PRIVATE
                )
            }

            val score = calculateScore(binding.q1) +
                    calculateScore(binding.q2) +
                    calculateScore(binding.q3) +
                    calculateScore(binding.q4) +
                    calculateScore(binding.q5) +
                    calculateScore(binding.q6) +
                    calculateScore(binding.q7) +
                    calculateScore(binding.q8)

            val editor = sharedPref.edit()
            editor.putInt("IADLSScore", score)
            editor.apply()

            Log.d("SharedPreferences", "Loading the save data...")
            Log.d(
                "SharedPreferences",
                "pIADLS Score: " + sharedPref.getInt("IADLSScore", -1).toString()
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
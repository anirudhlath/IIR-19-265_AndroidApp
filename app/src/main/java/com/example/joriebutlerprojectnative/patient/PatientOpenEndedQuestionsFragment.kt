/*
 *     Created by Anirudh Lath on 12/16/22, 11:44 AM
 *     anirudhlath@gmail.com
 *     Last modified 12/16/22, 11:43 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.patient

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.FragmentPatientOpenEndedQuestionsBinding
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 * Use the [PatientOpenEndedQuestionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientOpenEndedQuestionsFragment : Fragment() {

    private var _binding: FragmentPatientOpenEndedQuestionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.constraintLayout, PatientHomePageFragment()).commit()
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPatientOpenEndedQuestionsBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

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

    private fun submitSurvey() {
        if (!binding.q1.editText?.text.isNullOrEmpty()
            && !binding.q2.editText?.text.isNullOrEmpty()
            && !binding.q3.editText?.text.isNullOrEmpty()
            && !binding.q4.editText?.text.isNullOrEmpty()
            && !binding.q5.editText?.text.isNullOrEmpty()
        ) {
            val sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.patientData), Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("openEndedQuestionnaireQ1", binding.q1.editText?.text.toString())
            editor.putString("openEndedQuestionnaireQ2", binding.q2.editText?.text.toString())
            editor.putString("openEndedQuestionnaireQ3", binding.q3.editText?.text.toString())
            editor.putString("openEndedQuestionnaireQ4", binding.q4.editText?.text.toString())
            editor.putString("openEndedQuestionnaireQ5", binding.q5.editText?.text.toString())
            editor.apply()
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

        parentFragmentManager.beginTransaction()
            .replace(R.id.constraintLayout, PatientHomePageFragment()).commit()
        return
    }
}
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
import com.example.joriebutlerprojectnative.databinding.QuestionnairePainSelfEfficacyQuestionnaireBinding
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class PainSelfEfficacyQuestionnaireFragment : Fragment() {

    private var _binding: QuestionnairePainSelfEfficacyQuestionnaireBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =
            QuestionnairePainSelfEfficacyQuestionnaireBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        binding.buttonSubmitSurvey.setOnClickListener {
            submitSurvey()
        }


        return rootView
    }

    private fun submitSurvey() {
        if (!binding.q1Pseq.editText?.text.isNullOrEmpty()
            && !binding.q2Pseq.editText?.text.isNullOrEmpty()
            && !binding.q3Pseq.editText?.text.isNullOrEmpty()
            && !binding.q4Pseq.editText?.text.isNullOrEmpty()
            && !binding.q5Pseq.editText?.text.isNullOrEmpty()
            && !binding.q6Pseq.editText?.text.isNullOrEmpty()
            && !binding.q7Pseq.editText?.text.isNullOrEmpty()
            && !binding.q8Pseq.editText?.text.isNullOrEmpty()
            && !binding.q9Pseq.editText?.text.isNullOrEmpty()
            && !binding.q10Pseq.editText?.text.isNullOrEmpty()
        ) {
            val sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.patientData), Context.MODE_PRIVATE
            )

            val score =
                calculateScore(
                    binding.q1Pseq
                ) + calculateScore(
                    binding.q2Pseq
                ) + calculateScore(
                    binding.q3Pseq
                ) + calculateScore(
                    binding.q4Pseq
                ) + calculateScore(
                    binding.q5Pseq
                ) + calculateScore(
                    binding.q6Pseq
                ) + calculateScore(
                    binding.q7Pseq
                ) + calculateScore(
                    binding.q8Pseq
                ) + calculateScore(
                    binding.q9Pseq
                ) + calculateScore(
                    binding.q10Pseq
                )

            val editor = sharedPref.edit()
            editor.putInt("PseqScore", score)
            editor.putInt("pseq_q1", calculateScore(binding.q1Pseq))
            editor.putInt("pseq_q2", calculateScore(binding.q2Pseq))
            editor.putInt("pseq_q3", calculateScore(binding.q3Pseq))
            editor.putInt("pseq_q4", calculateScore(binding.q4Pseq))
            editor.putInt("pseq_q5", calculateScore(binding.q5Pseq))
            editor.putInt("pseq_q6", calculateScore(binding.q6Pseq))
            editor.putInt("pseq_q7", calculateScore(binding.q7Pseq))
            editor.putInt("pseq_q8", calculateScore(binding.q8Pseq))
            editor.putInt("pseq_q9", calculateScore(binding.q9Pseq))
            editor.putInt("pseq_q10", calculateScore(binding.q10Pseq))
            editor.putInt("PseqCompleted", 1)
            editor.apply()

            Log.d("SharedPreferences", "Loading the save data...")
            Log.d(
                "SharedPreferences",
                "PHQ2 Score: " + sharedPref.getInt("PseqScore", -1).toString()
            )
        } else {
            val contextView = requireView()
            Snackbar.make(
                contextView,
                "Please complete all the fields.",
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.constraintLayout, PatientSurveysFragment()).commit()
        return

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
            "0" -> {
                return 0
            }
            "1" -> {
                return 1
            }
            "2" -> {
                return 2
            }
            "3" -> {
                return 3
            }
            "4" -> {
                return 4
            }
            "5" -> {
                return 5
            }
            "6" -> {
                return 6
            }

        }
        return 0
    }

}
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
import com.example.joriebutlerprojectnative.databinding.QuestionnaireThoughtsAboutPainBinding
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class ThoughtsAboutPainFragment : Fragment() {
    private var _binding: QuestionnaireThoughtsAboutPainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            QuestionnaireThoughtsAboutPainBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        binding.buttonSubmitSurvey.setOnClickListener {
            submitSurvey()
        }


        return rootView
    }

    private fun calculateScore(textInputLayout: TextInputLayout): Int {
        when (textInputLayout.editText?.text.toString()) {
            "Not at all" -> {
                return 0
            }
            "To a slight degree" -> {
                return 1
            }
            "To a moderate degree" -> {
                return 2
            }
            "To a great degree" -> {
                return 3
            }
            "All the time" -> {
                return 4
            }
        }
        return 0
    }

    private fun submitSurvey() {
        if (!binding.q1ThoughtsAboutPain.editText?.text.isNullOrEmpty()
            &&!binding.q2ThoughtsAboutPain.editText?.text.isNullOrEmpty()
            &&!binding.q3ThoughtsAboutPain.editText?.text.isNullOrEmpty()
            &&!binding.q4ThoughtsAboutPain.editText?.text.isNullOrEmpty()
            &&!binding.q5ThoughtsAboutPain.editText?.text.isNullOrEmpty()
            &&!binding.q6ThoughtsAboutPain.editText?.text.isNullOrEmpty()
            &&!binding.q7ThoughtsAboutPain.editText?.text.isNullOrEmpty()
            &&!binding.q8ThoughtsAboutPain.editText?.text.isNullOrEmpty()
            &&!binding.q9ThoughtsAboutPain.editText?.text.isNullOrEmpty()
            &&!binding.q10ThoughtsAboutPain.editText?.text.isNullOrEmpty()
            &&!binding.q11ThoughtsAboutPain.editText?.text.isNullOrEmpty()
            &&!binding.q12ThoughtsAboutPain.editText?.text.isNullOrEmpty()
            &&!binding.q13ThoughtsAboutPain.editText?.text.isNullOrEmpty()
        ) {
            val sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.patientData), Context.MODE_PRIVATE
            )

            val score =
                calculateScore(
                    binding.q1ThoughtsAboutPain
                ) + calculateScore(
                    binding.q2ThoughtsAboutPain
                ) + calculateScore(
                    binding.q3ThoughtsAboutPain
                ) + calculateScore(
                    binding.q4ThoughtsAboutPain
                ) + calculateScore(
                    binding.q5ThoughtsAboutPain
                ) + calculateScore(
                    binding.q6ThoughtsAboutPain
                ) + calculateScore(
                    binding.q7ThoughtsAboutPain
                ) + calculateScore(
                    binding.q8ThoughtsAboutPain
                ) + calculateScore(
                    binding.q9ThoughtsAboutPain
                ) + calculateScore(
                    binding.q10ThoughtsAboutPain
                ) + calculateScore(
                    binding.q11ThoughtsAboutPain
                ) + calculateScore(
                    binding.q12ThoughtsAboutPain
                ) + calculateScore(
                    binding.q13ThoughtsAboutPain
                )

            val editor = sharedPref.edit()
            editor.putInt("ThoughtsAboutPainScore", score)
            editor.apply()

            Log.d("SharedPreferences", "Loading the save data...")
            Log.d(
                "SharedPreferences",
                "Thoughts About Pain Score: " + sharedPref.getInt("ThoughtsAboutPainScore", -1).toString()
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

}
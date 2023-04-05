/*
 *     Created by Anirudh Lath on 4/4/23, 8:40 PM
 *     anirudhlath@gmail.com
 *     Last modified 4/4/23, 8:40 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.surveys

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.QuestionnaireBriefHealthLiteracyScreeningToolBinding
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


/**
 * A simple [Fragment] subclass.
 * Use the [BriefHealthLiteracyScreeningToolFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BriefHealthLiteracyScreeningToolFragment : Fragment() {

    private var _binding: QuestionnaireBriefHealthLiteracyScreeningToolBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            QuestionnaireBriefHealthLiteracyScreeningToolBinding.inflate(inflater, container, false)
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

    private fun calculateScore(textInputLayout: TextInputLayout): Int {
        Log.d("CalculateScores", textInputLayout.editText?.text.toString()[0].toString())
        when (textInputLayout.editText?.text.toString()) {
            "Always" -> {
                return 1
            }
            "Not at all" -> {
                return 1
            }
            "Often" -> {
                return 2
            }
            "A little bit" -> {
                return 2
            }
            "Sometimes" -> {
                return 3
            }
            "Somewhat" -> {
                return 3
            }
            "Occasionally" -> {
                return 4
            }
            "Quite a bit" -> {
                return 4
            }
            "Never" -> {
                return 5
            }
            "Extremely" -> {
                return 5
            }
        }
        return 0
    }

    private fun submitSurvey() {
        if (!binding.q1.editText?.text.isNullOrEmpty()
            && !binding.q2.editText?.text.isNullOrEmpty()
            && !binding.q3.editText?.text.isNullOrEmpty()
            && !binding.q4.editText?.text.isNullOrEmpty()
        ) {
            val sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.patientData), Context.MODE_PRIVATE
            )

            val score = calculateScore(binding.q1) +
                    calculateScore(binding.q2) +
                    calculateScore(binding.q3) +
                    calculateScore(binding.q4)

            val editor = sharedPref.edit()
            editor.putInt("BriefScore", score)
            editor.putInt("brief_q1", calculateScore(binding.q1))
            editor.putInt("brief_q2", calculateScore(binding.q2))
            editor.putInt("brief_q3", calculateScore(binding.q3))
            editor.putInt("brief_q4", calculateScore(binding.q4))
            editor.putInt("BriefCompleted", 1)
            editor.apply()

            Log.d("SharedPreferences", "Loading the save data...")
            Log.d(
                "SharedPreferences",
                "Brief Score: " + sharedPref.getInt("BriefScore", -1).toString()
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

}
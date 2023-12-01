/*
 *     Created by Anirudh Lath in 2021
 *     anirudhlath@gmail.com
 *     Last modified 11/30/23, 6:38 PM
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
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.QuestionnaireStateTraitAnxietyInventoryBinding
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.roundToInt

/**
 * A simple [Fragment] subclass. Use the [StateTraitAnxietyInventoryFragment.newInstance] factory
 * method to create an instance of this fragment.
 */
class StateTraitAnxietyInventoryFragment : Fragment(), View.OnClickListener {

  private var _binding: QuestionnaireStateTraitAnxietyInventoryBinding? = null
  private val binding
    get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    activity
      ?.onBackPressedDispatcher
      ?.addCallback(
        this,
        object : OnBackPressedCallback(true) {
          override fun handleOnBackPressed() {
            parentFragmentManager
              .beginTransaction()
              .replace(R.id.constraintLayout, PatientSurveysFragment())
              .commit()
          }
        }
      )
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = QuestionnaireStateTraitAnxietyInventoryBinding.inflate(inflater, container, false)
    val rootView = _binding!!.root

    binding.buttonSubmitSurvey.setOnClickListener(this)

    return rootView
  }

  override fun onClick(v: View?) {
    if (v != null) {
      when (v.id) {
        R.id.buttonSubmitSurvey -> {

          if (
            !binding.q1Stai.editText?.text.isNullOrEmpty() &&
              !binding.q2Stai.editText?.text.isNullOrEmpty() &&
              !binding.q3Stai.editText?.text.isNullOrEmpty() &&
              !binding.q4Stai.editText?.text.isNullOrEmpty() &&
              !binding.q5Stai.editText?.text.isNullOrEmpty() &&
              !binding.q6Stai.editText?.text.isNullOrEmpty()
          ) {

            val sharedPref =
              requireActivity()
                .getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putInt("StaiScore", calculateScores())
            editor.putInt("stai_q1", 5 - calculateScore(binding.q1Stai))
            editor.putInt("stai_q2", calculateScore(binding.q2Stai))
            editor.putInt("stai_q3", calculateScore(binding.q3Stai))
            editor.putInt("stai_q4", 5 - calculateScore(binding.q4Stai))
            editor.putInt("stai_q5", 5 - calculateScore(binding.q5Stai))
            editor.putInt("stai_q6", calculateScore(binding.q6Stai))
            editor.putInt("StaiCompleted", 1)
            editor.apply()
            Log.d("SharedPreferences", "Loading the save data...")
            Log.d(
              "SharedPreferences",
              "STAI Score: " + sharedPref.getInt("StaiScore", -1).toString()
            )
          } else {
            val contextView = requireView()
            Snackbar.make(contextView, "Please complete all the fields.", Snackbar.LENGTH_SHORT)
              .show()
            return
          }

          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, PatientSurveysFragment())
            .commit()
          return
        }
      }
    }
  }

  private fun calculateScores(): Int {
    val arr = ArrayList<TextInputLayout>()
    arr.add(binding.q2Stai)
    arr.add(binding.q3Stai)
    arr.add(binding.q6Stai)
    var score = 0
    for (item in arr) {
      score += calculateScore(item)
    }
    arr.clear()

    // Reverse the scores for questions 1, 4, 5
    arr.add(binding.q1Stai)
    arr.add(binding.q4Stai)
    arr.add(binding.q5Stai)

    for (item in arr) {
      score += 5 - calculateScore(item)
    }

    Log.d("CalculateScores", "STAI Pre total Score: $score")

    Log.d("CalculateScores", "STAI total Score: " + ((score * 1.0 / 6.0) * 20.0))

    return (score / 6.0 * 20.0).roundToInt()
  }

  private fun calculateScore(textInputLayout: TextInputLayout): Int {
    when (textInputLayout.editText?.text.toString()) {
      "Not at all" -> {
        return 1
      }
      "Somewhat" -> {
        return 2
      }
      "Moderately so" -> {
        return 3
      }
      "Very much so" -> {
        return 4
      }
    }
    return 0
  }

  override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
    return if (enter) {
      AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
    } else {
      AnimationUtils.loadAnimation(activity, R.anim.fade_out)
    }
  }
}

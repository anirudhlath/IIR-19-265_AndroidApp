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
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.QuestionnaireGdsBinding
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

/**
 * A simple [Fragment] subclass. Use the [GdsFragment.newInstance] factory method to create an
 * instance of this fragment.
 */
class GdsFragment : Fragment() {

  private var _binding: QuestionnaireGdsBinding? = null
  private val binding
    get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = QuestionnaireGdsBinding.inflate(inflater, container, false)
    val rootView = _binding!!.root

    binding.buttonSubmitSurvey.setOnClickListener { submitSurvey() }

    return rootView
  }

  private fun calculateScore(textInputLayout: TextInputLayout, inverted: Boolean = false): Int {
    when (textInputLayout.editText?.text.toString()) {
      "Yes" -> {
        return if (inverted) 0 else 1
      }
      "No" -> {
        return if (inverted) 1 else 0
      }
    }
    return 0
  }

  private fun submitSurvey() {
    if (
      !binding.q1.editText?.text.isNullOrEmpty() &&
      !binding.q2.editText?.text.isNullOrEmpty() &&
      !binding.q3.editText?.text.isNullOrEmpty() &&
      !binding.q4.editText?.text.isNullOrEmpty() &&
      !binding.q5.editText?.text.isNullOrEmpty() &&
      !binding.q6.editText?.text.isNullOrEmpty() &&
      !binding.q7.editText?.text.isNullOrEmpty() &&
      !binding.q8.editText?.text.isNullOrEmpty() &&
      !binding.q9.editText?.text.isNullOrEmpty() &&
      !binding.q10.editText?.text.isNullOrEmpty() &&
      !binding.q11.editText?.text.isNullOrEmpty() &&
      !binding.q12.editText?.text.isNullOrEmpty() &&
      !binding.q13.editText?.text.isNullOrEmpty() &&
      !binding.q14.editText?.text.isNullOrEmpty() &&
      !binding.q15.editText?.text.isNullOrEmpty()
    ) {
      val sharedPref =
        requireActivity()
          .getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)

      val score = (
              calculateScore(binding.q2)
              + calculateScore(binding.q3)
              + calculateScore(binding.q4)
              + calculateScore(binding.q6)
              + calculateScore(binding.q8)
              + calculateScore(binding.q9)
              + calculateScore(binding.q10)
              + calculateScore(binding.q12)
              + calculateScore(binding.q14)
              + calculateScore(binding.q15)
              + calculateScore(binding.q1, true)
              + calculateScore(binding.q5, true)
              + calculateScore(binding.q7, true)
              + calculateScore(binding.q11, true)
              + calculateScore(binding.q13, true)
              )

      val editor = sharedPref.edit()
      editor.putInt("GdsScore", score)
      editor.putInt("Gds_q1", calculateScore(binding.q1, true))
      editor.putInt("Gds_q2", calculateScore(binding.q2))
      editor.putInt("Gds_q3", calculateScore(binding.q3))
      editor.putInt("Gds_q4", calculateScore(binding.q4))
      editor.putInt("Gds_q5", calculateScore(binding.q5, true))
      editor.putInt("Gds_q6", calculateScore(binding.q6))
      editor.putInt("Gds_q7", calculateScore(binding.q7, true))
      editor.putInt("Gds_q8", calculateScore(binding.q8))
      editor.putInt("Gds_q9", calculateScore(binding.q9))
      editor.putInt("Gds_q10", calculateScore(binding.q10))
      editor.putInt("Gds_q11", calculateScore(binding.q11, true))
      editor.putInt("Gds_q12", calculateScore(binding.q12))
      editor.putInt("Gds_q13", calculateScore(binding.q13, true))
      editor.putInt("Gds_q14", calculateScore(binding.q14))
      editor.putInt("Gds_q15", calculateScore(binding.q15))
      editor.putInt("GdsCompleted", 1)
      editor.apply()

      Log.d("SharedPreferences", "Loading the save data...")
      Log.d("SharedPreferences", "GDS Score: " + sharedPref.getInt("GdsScore", -1).toString())
    } else {
      val contextView = requireView()
      Snackbar.make(contextView, "Please complete all the fields.", Snackbar.LENGTH_SHORT).show()
      return
    }
    parentFragmentManager
      .beginTransaction()
      .replace(R.id.constraintLayout, PatientSurveysFragment())
      .commit()
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

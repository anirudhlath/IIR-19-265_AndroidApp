/*
 *     Created by Anirudh Lath in 2021
 *     anirudhlath@gmail.com
 *     Last modified 11/30/23, 6:38 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.dailySurveys

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.QuestionnaireGad2DailyBinding
import com.example.joriebutlerprojectnative.patient.PatientDailySurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDate

class Gad2DailyFragment : Fragment() {

  private var _binding: QuestionnaireGad2DailyBinding? = null
  private val binding
    get() = _binding!!

  override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
    return if (enter) {
      AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
    } else {
      AnimationUtils.loadAnimation(activity, R.anim.fade_out)
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = QuestionnaireGad2DailyBinding.inflate(inflater, container, false)
    val rootView = _binding!!.root

    binding.buttonSubmitSurvey.setOnClickListener { submitSurvey() }

    return rootView
  }

  private fun calculateScore(textInputLayout: TextInputLayout): Int {
    when (textInputLayout.editText?.text.toString().lowercase()) {
      "Not at all".lowercase() -> {
        return 0
      }

      "several times".lowercase() -> {
        return 1
      }

      "more than half the day".lowercase() -> {
        return 2
      }

      "nearly all day".lowercase() -> {
        return 3
      }
    }
    return 0
  }

  private fun submitSurvey() {
    if (!binding.q1.editText?.text.isNullOrEmpty() && !binding.q2.editText?.text.isNullOrEmpty()) {
      val sharedPref =
        requireActivity()
          .getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)

      val date = LocalDate.now().toString()

      val editor = sharedPref.edit()
      editor.putInt("Gad2DailyQ1_$date", calculateScore(binding.q1))
      editor.putInt("Gad2DailyQ2_$date", calculateScore(binding.q2))
      editor.putInt("Gad2DailyTotal_$date", calculateScore(binding.q2) + calculateScore(binding.q2))
      editor.putInt("Gad2DailyCompleted", 1)
      editor.putString("Gad2DailyCompletedTimestamp", date)
      editor.apply()
    } else {
      val contextView = requireView()
      Snackbar.make(contextView, "Please complete all the fields.", Snackbar.LENGTH_SHORT).show()
      return
    }
    parentFragmentManager
      .beginTransaction()
      .replace(R.id.constraintLayout, PatientDailySurveysFragment())
      .commit()
    return
  }
}

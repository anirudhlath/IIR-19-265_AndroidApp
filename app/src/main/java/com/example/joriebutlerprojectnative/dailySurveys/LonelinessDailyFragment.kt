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
import com.example.joriebutlerprojectnative.databinding.QuestionnaireLonelinessDailyBinding
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class LonelinessDailyFragment : Fragment() {

  private var _binding: QuestionnaireLonelinessDailyBinding? = null
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
    _binding = QuestionnaireLonelinessDailyBinding.inflate(inflater, container, false)
    val rootView = _binding!!.root

    binding.buttonSubmitSurvey.setOnClickListener { submitSurvey() }

    return rootView
  }

  private fun calculateScore(textInputLayout: TextInputLayout): Int {
    when (textInputLayout.editText?.text.toString()) {
      "Very hard" -> {
        return 3
      }
      "Somewhat hard" -> {
        return 2
      }
      "Not hard at all" -> {
        return 1
      }
      "Yes" -> {
        return 1
      }
      "No" -> {
        return 0
      }
    }
    return 0
  }

  private fun submitSurvey() {
    if (
      !binding.q1.editText?.text.isNullOrEmpty() &&
        !binding.q2.editText?.text.isNullOrEmpty() &&
        !binding.q3.editText?.text.isNullOrEmpty()
    ) {
      val sharedPref =
        requireActivity()
          .getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)

      val editor = sharedPref.edit()
      editor.putInt("ContextScore1", calculateScore(binding.q1))
      editor.putInt("ContextScore2", calculateScore(binding.q2))
      editor.putInt("ContextScore3", calculateScore(binding.q3))
      editor.putInt("ContextCompleted", 1)
      editor.apply()
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
}

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
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.QuestionnairePseq2DailyBinding
import com.example.joriebutlerprojectnative.patient.PatientDailySurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDate

class Pseq2DailyFragment : Fragment() {
  private var _binding: QuestionnairePseq2DailyBinding? = null
  private val binding
    get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = QuestionnairePseq2DailyBinding.inflate(inflater, container, false)
    val rootView = _binding!!.root

    binding.buttonSubmitSurvey.setOnClickListener { submitSurvey() }

    return rootView
  }

  private fun calculateScore(textInputLayout: TextInputLayout): Int {
    return textInputLayout.editText?.text.toString().split(' ')[0].toInt()
  }

  private fun submitSurvey() {
    if (!binding.q1.editText?.text.isNullOrEmpty() && !binding.q2.editText?.text.isNullOrEmpty()) {
      val sharedPref =
        requireActivity()
          .getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)

      val date = LocalDate.now().toString()

      val editor = sharedPref.edit()
      editor.putInt("Pseq2DailyQ1_$date", calculateScore(binding.q1))
      editor.putInt("Pseq2DailyQ2_$date", calculateScore(binding.q2))
      editor.putInt(
        "Pseq2DailyTotal_$date",
        (calculateScore(binding.q1) + calculateScore(binding.q2)) / 2
      )
      editor.putInt("Pseq2DailyCompleted", 1)
      editor.putString("Pseq2DailyCompletedTimestamp", date)
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

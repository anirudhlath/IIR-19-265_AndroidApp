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
import com.example.joriebutlerprojectnative.databinding.QuestionnaireMedicationDailyBinding
import com.example.joriebutlerprojectnative.patient.PatientDailySurveysFragment
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDate

/**
 * A simple [Fragment] subclass. Use the [MedicationDailyFragment.newInstance] factory method to create an
 * instance of this fragment.
 */
class MedicationDailyFragment : Fragment() {

  private var _binding: QuestionnaireMedicationDailyBinding? = null
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
    _binding = QuestionnaireMedicationDailyBinding.inflate(inflater, container, false)
    val rootView = _binding!!.root

    binding.buttonSubmitSurvey.setOnClickListener { submitSurvey() }

    return rootView
  }

  private fun calculateScore(textInputLayout: TextInputLayout): Int {
    when (textInputLayout.editText?.text.toString()) {
      "Yes" -> {
        return 1
      }
    }
    return 0
  }

  private fun submitSurvey() {
    if (
      !binding.q1.editText?.text.isNullOrEmpty()
    ) {
      val sharedPref =
        requireActivity()
          .getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)

      val date = LocalDate.now().toString()

      val editor = sharedPref.edit()
      editor.putInt("MedicationDailyScore_$date", calculateScore(binding.q1))
      editor.putInt("MedicationDailyCompleted", 1)
      editor.putString("MedicationDailyCompletedTimestamp", date)
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

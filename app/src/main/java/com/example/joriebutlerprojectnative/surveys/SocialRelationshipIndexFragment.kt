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
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.caregiver.CaregiverSurveysFragment
import com.example.joriebutlerprojectnative.databinding.QuestionnaireSocialRelationshipIndexBinding
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

/**
 * A simple [Fragment] subclass. Use the [SocialRelationshipIndexFragment.newInstance] factory
 * method to create an instance of this fragment.
 */
class SocialRelationshipIndexFragment : Fragment() {

  private var _binding: QuestionnaireSocialRelationshipIndexBinding? = null
  private val binding
    get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = QuestionnaireSocialRelationshipIndexBinding.inflate(inflater, container, false)
    val rootView = _binding!!.root

    binding.buttonSubmitSurvey.setOnClickListener { submitSurvey() }

    if (requireActivity()::class.java.simpleName == "CaregiverActivity") {
      binding.instructions.text =
        "For the person you are caretaker for, please complete the following."
    }

    return rootView
  }

  private fun calculateScore(textInputLayout: TextInputLayout): Int {
    Log.d("CalculateScores", textInputLayout.editText?.text.toString()[0].toString())
    when (textInputLayout.editText?.text.toString()[0].toString()) {
      "N" -> {
        return 1
      }
      "A" -> {
        return 2
      }
      "S" -> {
        return 3
      }
      "M" -> {
        return 4
      }
      "V" -> {
        return 5
      }
      "E" -> {
        return 6
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
      var sharedPref =
        requireActivity()
          .getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)

      if (requireActivity()::class.java.simpleName == "CaregiverActivity") {
        sharedPref =
          requireActivity()
            .getSharedPreferences(getString(R.string.caregiverData), Context.MODE_PRIVATE)
      }

      val score =
        calculateScore(binding.q1) + calculateScore(binding.q2) + calculateScore(binding.q3)

      val editor = sharedPref.edit()
      editor.putInt("SRIScore", score)
      editor.putInt("sri_q1", calculateScore(binding.q1))
      editor.putInt("sri_q2", calculateScore(binding.q2))
      editor.putInt("sri_q3", calculateScore(binding.q3))
      editor.putInt("SRICompleted", 1)
      editor.apply()

      Log.d("SharedPreferences", "Loading the save data...")
      Log.d("SharedPreferences", "SRI Score: " + sharedPref.getInt("SRIScore", -1).toString())
    } else {
      val contextView = requireView()
      Snackbar.make(contextView, "Please complete all the fields.", Snackbar.LENGTH_SHORT).show()
      return
    }
    if (requireActivity()::class.java.simpleName == "CaregiverActivity") {
      parentFragmentManager
        .beginTransaction()
        .replace(R.id.fragmentContainerView2, CaregiverSurveysFragment())
        .commit()
    } else {
      parentFragmentManager
        .beginTransaction()
        .replace(R.id.constraintLayout, PatientSurveysFragment())
        .commit()
    }
    return
  }
}

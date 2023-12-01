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
import com.example.joriebutlerprojectnative.databinding.QuestionnaireLubbenSocialNetworkScaleBinding
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

/**
 * A simple [Fragment] subclass. Use the [LubbenSocialNetworkScaleFragment.newInstance] factory
 * method to create an instance of this fragment.
 */
class LubbenSocialNetworkScaleFragment : Fragment() {

  private var _binding: QuestionnaireLubbenSocialNetworkScaleBinding? = null
  private val binding
    get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = QuestionnaireLubbenSocialNetworkScaleBinding.inflate(inflater, container, false)
    val rootView = _binding!!.root

    binding.buttonSubmitSurvey.setOnClickListener { submitSurvey() }

    return rootView
  }

  private fun calculateScore(textInputLayout: TextInputLayout): Int {
    when (textInputLayout.editText?.text.toString()) {
      "None" -> {
        return 0
      }
      "1" -> {
        return 1
      }
      "2" -> {
        return 2
      }
      "3 or 4" -> {
        return 3
      }
      "5 to 8" -> {
        return 4
      }
      "More than 9" -> {
        return 5
      }
    }
    return 0
  }

  private fun submitSurvey() {
    if (
      !binding.q1LSNS6.editText?.text.isNullOrEmpty() &&
        !binding.q2LSNS6.editText?.text.isNullOrEmpty() &&
        !binding.q3LSNS6.editText?.text.isNullOrEmpty() &&
        !binding.q4LSNS6.editText?.text.isNullOrEmpty() &&
        !binding.q5LSNS6.editText?.text.isNullOrEmpty() &&
        !binding.q6LSNS6.editText?.text.isNullOrEmpty()
    ) {
      val sharedPref =
        requireActivity()
          .getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)

      val score =
        calculateScore(binding.q1LSNS6) +
          calculateScore(binding.q2LSNS6) +
          calculateScore(binding.q3LSNS6) +
          calculateScore(binding.q4LSNS6) +
          calculateScore(binding.q5LSNS6) +
          calculateScore(binding.q6LSNS6)

      val editor = sharedPref.edit()
      editor.putInt("LSNS6Score", score)
      editor.putInt("lsns_q1", calculateScore(binding.q1LSNS6))
      editor.putInt("lsns_q2", calculateScore(binding.q2LSNS6))
      editor.putInt("lsns_q3", calculateScore(binding.q3LSNS6))
      editor.putInt("lsns_q4", calculateScore(binding.q4LSNS6))
      editor.putInt("lsns_q5", calculateScore(binding.q5LSNS6))
      editor.putInt("lsns_q6", calculateScore(binding.q6LSNS6))
      editor.putInt("LSNSCompleted", 1)
      editor.apply()

      Log.d("SharedPreferences", "Loading the save data...")
      Log.d("SharedPreferences", "LSNS6 Score: " + sharedPref.getInt("LSNS6Score", -1).toString())
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

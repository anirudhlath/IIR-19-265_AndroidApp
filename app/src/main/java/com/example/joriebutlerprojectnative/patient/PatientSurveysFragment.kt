/*
 *     Created by Anirudh Lath in 2021
 *     anirudhlath@gmail.com
 *     Last modified 11/30/23, 6:38 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.patient

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.*
import com.example.joriebutlerprojectnative.dailySurveys.MedicationDailyFragment
import com.example.joriebutlerprojectnative.databinding.FragmentPatientSurveysBinding
import com.example.joriebutlerprojectnative.surveys.*

/**
 * A simple [Fragment] subclass. Use the [PatientSurveysFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientSurveysFragment : Fragment(), OnClickListener {

  private var _binding: FragmentPatientSurveysBinding? = null
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
              .replace(R.id.constraintLayout, PatientHomePageFragment())
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
    _binding = FragmentPatientSurveysBinding.inflate(inflater, container, false)
    val rootView = _binding!!.root

    binding.buttonSTAI.setOnClickListener(this)
    binding.buttonGDS.setOnClickListener(this)
    binding.buttonPSEQ.setOnClickListener(this)
    binding.buttonThoughtsAboutPain.setOnClickListener(this)
    binding.buttonLoneliness.setOnClickListener(this)
    binding.buttonIsolation.setOnClickListener(this)
    binding.buttonActivitiesOfDailyLifeScale.setOnClickListener(this)
    binding.buttonInstrumentalActivitiesOfDailyLifeScale.setOnClickListener(this)
    binding.buttonContext.setOnClickListener(this)
    binding.buttonHealthLiteracy.setOnClickListener(this)
    binding.buttonSRI.setOnClickListener(this)
    binding.buttonCHAMPS.setOnClickListener(this)
    binding.buttonMedication.setOnClickListener(this)

    // Check which questionnaires have been completed already.
    val patientSharedPref =
      requireActivity().getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)

    checkCompletion(patientSharedPref, "AdlsCompleted", binding.buttonActivitiesOfDailyLifeScale)
    checkCompletion(patientSharedPref, "BriefCompleted", binding.buttonHealthLiteracy)
    checkCompletion(patientSharedPref, "ContextCompleted", binding.buttonContext)
    checkCompletion(
      patientSharedPref,
      "IADLSCompleted",
      binding.buttonInstrumentalActivitiesOfDailyLifeScale
    )
    checkCompletion(patientSharedPref, "LonelinessCompleted", binding.buttonLoneliness)
    checkCompletion(patientSharedPref, "LSNSCompleted", binding.buttonIsolation)
    checkCompletion(patientSharedPref, "PseqCompleted", binding.buttonPSEQ)
    checkCompletion(patientSharedPref, "GdsCompleted", binding.buttonGDS)
    checkCompletion(patientSharedPref, "SRICompleted", binding.buttonSRI)
    checkCompletion(patientSharedPref, "StaiCompleted", binding.buttonSTAI)
    checkCompletion(
      patientSharedPref,
      "ThoughtsAboutPainCompleted",
      binding.buttonThoughtsAboutPain
    )
    checkCompletion(patientSharedPref, "CHAMPSCompleted", binding.buttonCHAMPS)
    checkCompletion(patientSharedPref, "MedicationCompleted", binding.buttonMedication)

    return rootView
  }

  private fun checkCompletion(sharedPref: SharedPreferences, string: String, button: Button) {
    val completed = sharedPref.getInt(string, 0)
    if (completed == 1) {
      button.isEnabled = false
      val img: Drawable =
        ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_check_24)!!
      button.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
    }
  }

  override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
    return if (enter) {
      AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
    } else {
      AnimationUtils.loadAnimation(activity, R.anim.fade_out)
    }
  }

  override fun onClick(v: View?) {
    if (v != null) {
      when (v.id) {
        R.id.buttonSTAI -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, StateTraitAnxietyInventoryFragment())
            .commit()

          return
        }
//        R.id.buttonPHQ -> {
//          parentFragmentManager
//            .beginTransaction()
//            .replace(R.id.constraintLayout, Phq2Fragment())
//            .commit()
//
//          return
//        }
        R.id.buttonGDS -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, GdsFragment())
            .commit()

          return
        }
        R.id.buttonPSEQ -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, PainSelfEfficacyQuestionnaireFragment())
            .commit()

          return
        }
        R.id.buttonThoughtsAboutPain -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, ThoughtsAboutPainFragment())
            .commit()

          return
        }
        R.id.buttonLoneliness -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, LonelinessFragment())
            .commit()

          return
        }
        R.id.buttonIsolation -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, LubbenSocialNetworkScaleFragment())
            .commit()

          return
        }
        R.id.buttonActivitiesOfDailyLifeScale -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, ActivitiesOfDailyLifeScaleFragment())
            .commit()

          return
        }
        R.id.buttonInstrumentalActivitiesOfDailyLifeScale -> {

          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, InstrumentalActivitiesOfDailyLifeScaleFragment())
            .commit()

          return
        }
        R.id.buttonSRI -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, SocialRelationshipIndexFragment())
            .commit()

          return
        }
        R.id.buttonContext -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, ContextFragment())
            .commit()

          return
        }
        R.id.buttonHealthLiteracy -> {

          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, BriefHealthLiteracyScreeningToolFragment())
            .commit()

          return
        }
        R.id.buttonCHAMPS -> {

          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, ChampsFragment())
            .commit()

          return
        }
        R.id.buttonMedication -> {

          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, MedicationFragment())
            .commit()

          return
        }
      }
    }
  }
}

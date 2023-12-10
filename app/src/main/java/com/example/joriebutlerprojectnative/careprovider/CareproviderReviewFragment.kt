/*
 *     Created by Anirudh Lath in 2021
 *     anirudhlath@gmail.com
 *     Last modified 11/30/23, 6:38 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.careprovider

import android.animation.ObjectAnimator
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.joriebutlerprojectnative.ImageSliderAdapter
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.SliderItem
import com.example.joriebutlerprojectnative.databinding.FragmentCareproviderReviewBinding
import com.google.android.material.snackbar.Snackbar
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.LocalDate

class CareproviderReviewFragment : Fragment(), OnClickListener {

  private var _binding: FragmentCareproviderReviewBinding? = null
  private val binding
    get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentCareproviderReviewBinding.inflate(inflater, container, false)
    val rootView = _binding!!.root

    binding.saveButton.setOnClickListener { exportButton() }
    binding.deleteButton.setOnClickListener { resetData() }
    binding.helpButton.setOnClickListener(this)

    prepareReviewPage()

    return rootView
  }

  private fun prepareReviewPage() {
    GlobalScope.launch {
      val sharedPref =
        requireActivity()
          .getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)
      updatePatientData(sharedPref)
      updateCaregiverData()
      updateAnxietyProgressBar(sharedPref)
      updateDepressionProgressBar(sharedPref)
      updateSelfEfficacyProgressBar(sharedPref)
      updateThoughtsAboutPainProgressBar(sharedPref)
      updateLonelinessScoreProgressBar(sharedPref)
      updateSocialIsolationProgressBar(sharedPref)
      updateDifficultyGettingMedicationProgressBar(sharedPref)
      updateDifficultyAffordingCareProgressBar(sharedPref)
      updateDifficultyGettingToClinicProgressBar(sharedPref)
      updateAdlsProgressBar()
      updateHealthLiteracyProgressBar(sharedPref)
      updateOpenEndedQuestions(sharedPref)
      updateImageSlider(sharedPref)
      updateCaregiverBurdenScaleProgressBar()
      updateIadlsProgressBar()
      updatePatientCaregiverRelationshipProgressBar()
    }
  }

  private fun updateImageSlider(sharedPref: SharedPreferences) {
    val sliderView = binding.imageSlider
    val adapter = ImageSliderAdapter(requireContext())
    val labelArray =
      arrayOf(
        "What matters the most to me",
        "Where I keep my medicine",
        "Front of my house",
        "Second entry door",
        "Bedroom #1",
        "Bedroom #2",
        "My kitchen",
        "My bathroom",
        "Medical Equipment #1",
        "Medical Equipment #2"
      )

    // Add images to slider
    for (i in 1..10) {
      if (!sharedPref.getString("photo$i", "").isNullOrEmpty()) {
        adapter.addItem(SliderItem(labelArray[i - 1], sharedPref.getString("photo$i", "")))
        binding.imageIndicator.visibility = View.INVISIBLE
      }
    }
    sliderView.setSliderAdapter(adapter)
    requireActivity().runOnUiThread {
      sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
      sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
      sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
    }
    sliderView.indicatorSelectedColor = Color.WHITE
    sliderView.indicatorUnselectedColor = Color.GRAY
    sliderView.scrollTimeInSec = 4
    sliderView.startAutoCycle()
  }

  override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
    return if (enter) {
      AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
    } else {
      AnimationUtils.loadAnimation(activity, R.anim.fade_out)
    }
  }

  private fun updatePatientData(sharedPref: SharedPreferences) {
    requireActivity().runOnUiThread { loadImage("profilePhotoURI", binding.imageView, sharedPref) }
    binding.patientFullNameLabel.text =
      sharedPref.getString("fName", "NIL") + " " + sharedPref.getString("lName", "NIL")
    binding.patientDobLabel.text = sharedPref.getString("dob", "NIL")
    binding.patientMrnLabel.text = sharedPref.getString("mrn", "NIL")
    binding.patientTravelLabel.text = sharedPref.getString("travel", "NIL")
  }

  private fun updateCaregiverData() {
    val sharedPref =
      requireActivity()
        .getSharedPreferences(getString(R.string.caregiverData), Context.MODE_PRIVATE)
    binding.caregiverFullNameLabel.text = sharedPref.getString("fullName", "NIL")
    binding.caregiverRelationshipLabel.text = sharedPref.getString("relationship", "NIL")
    binding.caregiverLocationLabel.text = sharedPref.getString("location", "NIL")
    binding.caregiverFrequencyLabel.text = sharedPref.getString("frequency", "NIL")
  }

  private fun loadImage(
    imageName: String,
    imageView: ImageView,
    sharedPreferences: SharedPreferences
  ) {
    try {
      //            Picasso.get().load(sharedPreferences.getString(imageName, "")).resize(0,
      // 100).into(imageView)
      Glide.with(this)
        .load(sharedPreferences.getString(imageName, ""))
        .centerInside()
        .into(imageView)
    } catch (e: Exception) {
      // Handling the null pointer exception
    }
  }

  private fun updateAnxietyProgressBar(sharedPref: SharedPreferences) {
    val staiScore = sharedPref.getInt("StaiScore", -1)

    if (staiScore != -1) {
      binding.anxietyProgress.isIndeterminate = false
      binding.anxietyProgress.max = 80
      activity?.runOnUiThread {
        ObjectAnimator.ofInt(binding.anxietyProgress, "progress", staiScore)
          .setDuration(500)
          .start()
      }

      binding.anxietyProgress.tooltipText = "$staiScore/80"
      binding.anxietyLabel.tooltipText = "$staiScore/80"

      when (staiScore) {
        in 20..40 -> {
          binding.anxietyProgress.setIndicatorColor(Color.parseColor("#69B34C"))
        }
        in 41..60 -> {
          binding.anxietyProgress.setIndicatorColor(Color.parseColor("#FF8E15"))
        }
        else -> {
          binding.anxietyProgress.setIndicatorColor(Color.parseColor("#FF0D0D"))
        }
      }
    }
  }

  private fun updateDepressionProgressBar(sharedPref: SharedPreferences) {
    val score = sharedPref.getInt("Phq2Score", -1)

    if (score != -1) {
      val progressBar = binding.depressionProgressBar
      val label = binding.depressionLabel

      progressBar.max = 6
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
      }
      progressBar.tooltipText = "$score/${progressBar.max}"
      label.tooltipText = "$score/${progressBar.max}"

      when (score) {
        in 0..2 -> {
          progressBar.setIndicatorColor(Color.parseColor("#69B34C"))
        }
        in 3..6 -> {
          progressBar.setIndicatorColor(Color.parseColor("#FF0D0D"))
        }
      }
    }
  }

  private fun updateSelfEfficacyProgressBar(sharedPref: SharedPreferences) {
    val score = sharedPref.getInt("PseqScore", -1)

    if (score != -1) {
      val progressBar = binding.selfEfficacyProgressBar
      val label = binding.selfEfficacyLabel

      progressBar.max = 60
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
      }
      progressBar.tooltipText = "$score/${progressBar.max}"
      label.tooltipText = "$score/${progressBar.max}"

      when (score) {
        in 0..20 -> {
          progressBar.setIndicatorColor(Color.parseColor("#69B34C"))
        }
        in 21..60 -> {
          progressBar.setIndicatorColor(Color.parseColor("#FF0D0D"))
        }
      }
    }
  }

  private fun updateThoughtsAboutPainProgressBar(sharedPref: SharedPreferences) {
    val score = sharedPref.getInt("ThoughtsAboutPainScore", -1)

    if (score != -1) {
      val progressBar = binding.thoughtsAboutPainProgressBar
      val label = binding.thoughtsAboutPainLabel

      progressBar.max = 52
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
      }
      progressBar.tooltipText = "$score/${progressBar.max}"
      label.tooltipText = "$score/${progressBar.max}"

      when (score) {
        in 0..29 -> {
          progressBar.setIndicatorColor(Color.parseColor("#69B34C"))
        }
        in 30..52 -> {
          progressBar.setIndicatorColor(Color.parseColor("#FF0D0D"))
        }
      }
    }
  }

  private fun updateLonelinessScoreProgressBar(sharedPref: SharedPreferences) {
    val score = sharedPref.getInt("LonelinessScaleScore", -1)

    if (score != -1) {
      val progressBar = binding.LonelinessProgressBar
      val label = binding.LonelinessLabel

      progressBar.max = 9
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
      }
      progressBar.tooltipText = "$score/${progressBar.max}"
      label.tooltipText = "$score/${progressBar.max}"

      when (score) {
        in 3..5 -> {
          progressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
        }
        in 6..9 -> {
          progressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
        }
      }
    }
  }

  private fun updatePatientCaregiverRelationshipProgressBar() {
    var sharedPref =
      requireActivity().getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)
    val patientScore = sharedPref.getInt("SRIScore", -1)

    sharedPref =
      requireActivity()
        .getSharedPreferences(getString(R.string.caregiverData), Context.MODE_PRIVATE)

    val caregiverScore = sharedPref.getInt("SRIScore", -1)

    val label = binding.sriLabel
    val patientProgressBar = binding.sriPatientProgressBar
    val caregiverProgressBar = binding.sriCaregiverProgressBar

    if (patientScore != -1) {
      patientProgressBar.max = 18
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(patientProgressBar, "progress", patientScore).setDuration(500).start()
      }
      when (patientScore) {
        in 14..18 -> {
          patientProgressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
        }
        in 3..13 -> {
          patientProgressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
        }
      }
    }

    if (caregiverScore != -1) {
      caregiverProgressBar.max = 18
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(caregiverProgressBar, "progress", caregiverScore)
          .setDuration(500)
          .start()
      }
      when (caregiverScore) {
        in 14..18 -> {
          caregiverProgressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
        }
        in 3..13 -> {
          caregiverProgressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
        }
      }
    }

    if (patientScore != -1 && caregiverScore != -1) {
      label.tooltipText =
        "Patient: $patientScore/${patientProgressBar.max}\nCaregiver: $caregiverScore/${patientProgressBar.max}"
    } else if (patientScore != -1) {
      label.tooltipText = "Patient: $patientScore/${patientProgressBar.max}"
    } else if (caregiverScore != -1) {
      label.tooltipText = "Caregiver: $caregiverScore/${patientProgressBar.max}"
    }
  }

  private fun updateIadlsProgressBar() {
    var sharedPref =
      requireActivity().getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)
    val patientScore = sharedPref.getInt("IADLSScore", -1)
    val patientGender = sharedPref.getString("gender", "")

    sharedPref =
      requireActivity()
        .getSharedPreferences(getString(R.string.caregiverData), Context.MODE_PRIVATE)

    val caregiverScore = sharedPref.getInt("IADLSScore", -1)
    val caregiverGender = sharedPref.getString("gender", "")

    val label = binding.iadlsLabel
    val patientProgressBar = binding.iadlsPatientProgressBar
    val caregiverProgressBar = binding.iadlsCaregiverProgressBar

    if (patientScore != -1) {

      if (patientGender == "Male") {
        patientProgressBar.max = 5
      } else {
        patientProgressBar.max = 8
      }

      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(patientProgressBar, "progress", patientScore).setDuration(500).start()
      }
    }

    if (caregiverScore != -1) {

      if (caregiverGender == "Male") {
        caregiverProgressBar.max = 5
      } else {
        caregiverProgressBar.max = 8
      }

      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(caregiverProgressBar, "progress", caregiverScore)
          .setDuration(500)
          .start()
      }
    }

    if (patientScore != -1 && caregiverScore != -1) {
      label.tooltipText =
        "Patient: $patientScore/${patientProgressBar.max}\nCaregiver: $caregiverScore/${caregiverProgressBar.max}"
    } else if (patientScore != -1) {
      label.tooltipText = "Patient: $patientScore/${patientProgressBar.max}"
    } else if (caregiverScore != -1) {
      label.tooltipText = "Caregiver: $caregiverScore/${caregiverProgressBar.max}"
    }
  }

  private fun updateSocialIsolationProgressBar(sharedPref: SharedPreferences) {
    val score = sharedPref.getInt("LSNS6Score", -1)

    if (score != -1) {
      val progressBar = binding.SocialIsolationProgressBar
      val label = binding.SocialIsolationLabel

      progressBar.max = 30
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
      }
      progressBar.tooltipText = "$score/${progressBar.max}"
      label.tooltipText = "$score/${progressBar.max}"

      when (score) {
        in 12..30 -> {
          progressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
        }
        in 0..11 -> {
          progressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
        }
      }
    }
  }

  private fun updateAdlsProgressBar() {
    var sharedPref =
      requireActivity().getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)
    val patientScore = sharedPref.getInt("AdlsScore", -1)
    val patientProgressBar = binding.patientAdlsProgressBar
    val label = binding.patientAdlsLabel

    sharedPref =
      requireActivity()
        .getSharedPreferences(getString(R.string.caregiverData), Context.MODE_PRIVATE)

    val caregiverScore = sharedPref.getInt("AdlsScore", -1)
    val caregiverProgressBar = binding.caregiverAdlsProgressBar

    if (patientScore != -1) {
      patientProgressBar.max = 6
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(patientProgressBar, "progress", patientScore).setDuration(500).start()
      }
      patientProgressBar.tooltipText = "$patientScore/${patientProgressBar.max}"

      when (patientScore) {
        in 6 downTo 5 -> {
          patientProgressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
        }
        in 4 downTo 3 -> {
          patientProgressBar.setIndicatorColor(Color.parseColor("#FF8E15")) // Orange
        }
        in 2 downTo 0 -> {
          patientProgressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
        }
      }
    }

    if (caregiverScore != -1) {
      caregiverProgressBar.max = 6
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(caregiverProgressBar, "progress", caregiverScore)
          .setDuration(500)
          .start()
      }
      caregiverProgressBar.tooltipText = "$caregiverProgressBar/${caregiverProgressBar.max}"

      when (caregiverScore) {
        in 6 downTo 5 -> {
          caregiverProgressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
        }
        in 4 downTo 3 -> {
          caregiverProgressBar.setIndicatorColor(Color.parseColor("#FF8E15")) // Orange
        }
        in 2 downTo 0 -> {
          caregiverProgressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
        }
      }
    }

    if (patientScore != -1 && caregiverScore != -1) {
      label.tooltipText =
        "Patient: $patientScore/${patientProgressBar.max}\nCaregiver: $caregiverScore/${caregiverProgressBar.max}"
    } else if (patientScore != -1) {
      label.tooltipText = "Patient: $patientScore/${patientProgressBar.max}"
    } else if (caregiverScore != -1) {
      label.tooltipText = "Caregiver: $caregiverScore/${caregiverProgressBar.max}"
    }
  }

  private fun updateHealthLiteracyProgressBar(sharedPref: SharedPreferences) {
    val score = sharedPref.getInt("BriefScore", -1)

    if (score != -1) {
      val progressBar = binding.HealthLiteracyProgressBar
      val label = binding.HealthLiteracyLabel

      progressBar.max = 20
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
      }
      progressBar.tooltipText = "$score/${progressBar.max}"
      label.tooltipText = "$score/${progressBar.max}"

      when (score) {
        in 17..20 -> {
          progressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
        }
        in 13..16 -> {
          progressBar.setIndicatorColor(Color.parseColor("#FF8E15")) // Orange
        }
        in 4..12 -> {
          progressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
        }
      }
    }
  }

  private fun updateDifficultyGettingMedicationProgressBar(sharedPref: SharedPreferences) {
    val score = sharedPref.getInt("ContextScore1", -1)

    if (score != -1) {
      val progressBar = binding.DifficultyGettingMedicationProgressBar
      val label = binding.DifficultyGettingMedicationLabel

      progressBar.max = 3
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
      }
      progressBar.tooltipText = "$score/${progressBar.max}"
      label.tooltipText = "$score/${progressBar.max}"

      when (score) {
        1 -> {
          progressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
        }
        2 -> {
          progressBar.setIndicatorColor(Color.parseColor("#FF8E15")) // Orange
        }
        3 -> {
          progressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
        }
      }
    }
  }

  private fun updateDifficultyGettingToClinicProgressBar(sharedPref: SharedPreferences) {
    val score = sharedPref.getInt("ContextScore3", -1)

    if (score != -1) {
      val progressBar = binding.DifficultyGettingToClinicProgressBar
      val label = binding.DifficultyGettingToClinicLabel

      progressBar.max = 1
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(progressBar, "progress", 1).setDuration(500).start()
      }
      progressBar.tooltipText = "$score/${progressBar.max}"
      label.tooltipText = "$score/${progressBar.max}"

      when (score) {
        0 -> {
          progressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
        }
        1 -> {
          progressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
        }
      }
    }
  }

  private fun updateDifficultyAffordingCareProgressBar(sharedPref: SharedPreferences) {
    val score = sharedPref.getInt("ContextScore2", -1)

    if (score != -1) {
      val progressBar = binding.DifficultyAffordingCareProgressBar
      val label = binding.DifficultyAffordingCareLabel

      progressBar.max = 3
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
      }
      progressBar.tooltipText = "$score/${progressBar.max}"
      label.tooltipText = "$score/${progressBar.max}"

      when (score) {
        1 -> {
          progressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
        }
        2 -> {
          progressBar.setIndicatorColor(Color.parseColor("#FF8E15")) // Orange
        }
        3 -> {
          progressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
        }
      }
    }
  }

  private fun updateCaregiverBurdenScaleProgressBar() {
    val sharedPref =
      requireActivity()
        .getSharedPreferences(getString(R.string.caregiverData), Context.MODE_PRIVATE)
    val score = sharedPref.getInt("CBSScore", -1)

    if (score != -1) {
      val progressBar = binding.caregiverWorkloadProgressBar
      val label = binding.caregiverWorkloadLabel

      progressBar.max = 88
      requireActivity().runOnUiThread {
        ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
      }
      progressBar.tooltipText = "$score/${progressBar.max}"
      label.tooltipText = "$score/${progressBar.max}"

      when (score) {
        in 0..17 -> {
          progressBar.setIndicatorColor(Color.parseColor("#69B34C"))
        }
        in 18..88 -> {
          progressBar.setIndicatorColor(Color.parseColor("#FF0D0D"))
        }
      }
    }
  }

  private fun updateOpenEndedQuestions(sharedPref: SharedPreferences) {
    val q1 = sharedPref.getString("openEndedQuestionnaireQ1", "No response yet")
    val q2 = sharedPref.getString("openEndedQuestionnaireQ2", "No response yet")
    val q3 = sharedPref.getString("openEndedQuestionnaireQ3", "No response yet")
    val q4 = sharedPref.getString("openEndedQuestionnaireQ4", "No response yet")
    val q5 = sharedPref.getString("openEndedQuestionnaireQ5", "No response yet")
    val q6 = sharedPref.getString("openEndedQuestionnaireQ6", "No response yet")

    binding.q1.hint = q1
    binding.q2.hint = q2
    binding.q3.hint = q3
    binding.q4.hint = q4
    binding.q5.hint = q5
    binding.q6.hint = q6
  }

  private fun resetData() {
    val patientPref =
      requireActivity()
        .getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)
        .edit()
        .clear()
        .apply()
    val caregiverPref =
      requireActivity()
        .getSharedPreferences(getString(R.string.caregiverData), Context.MODE_PRIVATE)
        .edit()
        .clear()
        .apply()

    val contextView = requireView()
    Snackbar.make(contextView, "Data has been reset successfully.", Snackbar.LENGTH_LONG).show()

    parentFragmentManager
      .beginTransaction()
      .replace(R.id.fragmentContainerView3, CareproviderReviewFragment())
      .commit()
  }

  private fun exportData() {
    val patientPref =
      requireActivity().getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)
    val caregiverPref =
      requireActivity()
        .getSharedPreferences(getString(R.string.caregiverData), Context.MODE_PRIVATE)
    val baseDir =
      Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath
    val fileName =
      LocalDate.now().toString() +
        "_" +
        patientPref.getString("fName", "NIL").toString().uppercase()[0] +
        patientPref.getString("lName", "NIL").toString().uppercase()[0] +
        ".csv"
    val filePath = baseDir + File.separator + fileName

    val writer =
      try {
        Files.newBufferedWriter(Paths.get(filePath))
      } catch (e: Exception) {
        Log.e("CareproviderReview", "Error creating file: $e")
        return
      }

    val csvPrinter =
      CSVPrinter(
        writer,
        CSVFormat.DEFAULT.withHeader(
          "First Name",
          "Last Name",
          "DOB",
          "SSN",
          "Travel",
          "Caregiver Name",
          "Relationship",
          "Location",
          "Frequency",
          "patient_adls_total",
          "patient_adls_q1",
          "patient_adls_q2",
          "patient_adls_q3",
          "patient_adls_q4",
          "patient_adls_q5",
          "patient_adls_q6",
          "patient_brief_total",
          "patient_brief_q1",
          "patient_brief_q2",
          "patient_brief_q3",
          "patient_brief_q4",
          "patient_context_q1",
          "patient_context_q2",
          "patient_context_q3",
          "patient_iadls_total",
          "patient_iadls_q1",
          "patient_iadls_q2",
          "patient_iadls_q3",
          "patient_iadls_q4",
          "patient_iadls_q5",
          "patient_iadls_q6",
          "patient_iadls_q7",
          "patient_iadls_q8",
          "patient_loneliness_total",
          "patient_loneliness_q1",
          "patient_loneliness_q2",
          "patient_loneliness_q3",
          "patient_lsns_total",
          "patient_lsns_q1",
          "patient_lsns_q2",
          "patient_lsns_q3",
          "patient_lsns_q4",
          "patient_lsns_q5",
          "patient_lsns_q6",
          "patient_pseq_total",
          "patient_pseq_q1",
          "patient_pseq_q2",
          "patient_pseq_q3",
          "patient_pseq_q4",
          "patient_pseq_q5",
          "patient_pseq_q6",
          "patient_pseq_q7",
          "patient_pseq_q8",
          "patient_pseq_q9",
          "patient_pseq_q10",
          "patient_phq2_total",
          "patient_phq2_q1",
          "patient_phq2_q2",
          "patient_sri_total",
          "patient_sri_q1",
          "patient_sri_q2",
          "patient_sri_q3",
          "patient_stai_total",
          "patient_stai_q1",
          "patient_stai_q2",
          "patient_stai_q3",
          "patient_stai_q4",
          "patient_stai_q5",
          "patient_stai_q6",
          "patient_thoughtsAboutPain_total",
          "patient_thoughtsAboutPain_q1",
          "patient_thoughtsAboutPain_q2",
          "patient_thoughtsAboutPain_q3",
          "patient_thoughtsAboutPain_q4",
          "patient_thoughtsAboutPain_q5",
          "patient_thoughtsAboutPain_q6",
          "patient_thoughtsAboutPain_q7",
          "patient_thoughtsAboutPain_q8",
          "patient_thoughtsAboutPain_q9",
          "patient_thoughtsAboutPain_q10",
          "patient_thoughtsAboutPain_q11",
          "patient_thoughtsAboutPain_q12",
          "patient_thoughtsAboutPain_q13",
          "patient_champs_q1.1",
          "patient_champs_q1.2",
          "patient_champs_q2.1",
          "patient_champs_q2.2",
          "patient_champs_q3.1",
          "patient_champs_q3.2",
          "patient_champs_q4.1",
          "patient_champs_q4.2",
          "patient_champs_q5.1",
          "patient_champs_q5.2",
          "patient_champs_q6.1",
          "patient_champs_q6.2",
          "patient_champs_q7.1",
          "patient_champs_q7.2",
          "patient_champs_q8.1",
          "patient_champs_q8.2",
          "patient_champs_q9.1",
          "patient_champs_q9.2",
          "patient_champs_q10.1",
          "patient_champs_q10.2",
          "patient_champs_q11.1",
          "patient_champs_q11.2",
          "patient_champs_q12.1",
          "patient_champs_q12.2",
          "patient_champs_q13.1",
          "patient_champs_q13.2",
          "patient_champs_q14.1",
          "patient_champs_q14.2",
          "patient_champs_q15.1",
          "patient_champs_q15.2",
          "patient_champs_q16.1",
          "patient_champs_q16.2",
          "patient_champs_q17.1",
          "patient_champs_q17.2",
          "patient_champs_q18.1",
          "patient_champs_q18.2",
          "patient_champs_q19.1",
          "patient_champs_q19.2",
          "patient_champs_q20.1",
          "patient_champs_q20.2",
          "patient_champs_q21.1",
          "patient_champs_q21.2",
          "patient_champs_q22.1",
          "patient_champs_q22.2",
          "patient_champs_q23.1",
          "patient_champs_q23.2",
          "patient_champs_q24.1",
          "patient_champs_q24.2",
          "patient_champs_q25.1",
          "patient_champs_q25.2",
          "patient_champs_q26.1",
          "patient_champs_q26.2",
          "patient_champs_q27.1",
          "patient_champs_q27.2",
          "patient_champs_q28.1",
          "patient_champs_q28.2",
          "patient_champs_q29.1",
          "patient_champs_q29.2",
          "patient_champs_q30.1",
          "patient_champs_q30.2",
          "patient_champs_q31.1",
          "patient_champs_q31.2",
          "patient_champs_q32.1",
          "patient_champs_q32.2",
          "patient_champs_q33.1",
          "patient_champs_q33.2",
          "patient_champs_q34.1",
          "patient_champs_q34.2",
          "patient_champs_q35.1",
          "patient_champs_q35.2",
          "patient_champs_q36.1",
          "patient_champs_q36.2",
          "patient_champs_q37.1",
          "patient_champs_q37.2",
          "patient_champs_q38.1",
          "patient_champs_q38.2",
          "patient_champs_q39.1",
          "patient_champs_q39.2",
          "patient_champs_q40.1",
          "patient_champs_q40.2",
          "patient_champs_q41.1",
          "patient_champs_q41.2",
          "patient_openEnded_q1",
          "patient_openEnded_q2",
          "patient_openEnded_q3",
          "patient_openEnded_q4",
          "patient_openEnded_q5",
          "patient_openEnded_q6",
          "caregiver_cbs_total",
          "caregiver_cbs_q1",
          "caregiver_cbs_q2",
          "caregiver_cbs_q3",
          "caregiver_cbs_q4",
          "caregiver_cbs_q5",
          "caregiver_cbs_q6",
          "caregiver_cbs_q7",
          "caregiver_cbs_q8",
          "caregiver_cbs_q9",
          "caregiver_cbs_q10",
          "caregiver_cbs_q11",
          "caregiver_cbs_q12",
          "caregiver_cbs_q13",
          "caregiver_cbs_q14",
          "caregiver_cbs_q15",
          "caregiver_cbs_q16",
          "caregiver_cbs_q17",
          "caregiver_cbs_q18",
          "caregiver_cbs_q19",
          "caregiver_cbs_q20",
          "caregiver_cbs_q21",
          "caregiver_cbs_q22",
          "caregiver_adls_total",
          "caregiver_adls_q1",
          "caregiver_adls_q2",
          "caregiver_adls_q3",
          "caregiver_adls_q4",
          "caregiver_adls_q5",
          "caregiver_adls_q6",
          "caregiver_iadls_total",
          "caregiver_iadls_q1",
          "caregiver_iadls_q2",
          "caregiver_iadls_q3",
          "caregiver_iadls_q4",
          "caregiver_iadls_q5",
          "caregiver_iadls_q6",
          "caregiver_iadls_q7",
          "caregiver_iadls_q8",
          "caregiver_sri_total",
          "caregiver_sri_q1",
          "caregiver_sri_q2",
          "caregiver_sri_q3"
        )
      )

    csvPrinter.printRecord(
      patientPref.getString("fName", "NIL"),
      patientPref.getString("lName", "NIL"),
      patientPref.getString("dob", "NIL"),
      patientPref.getString("mrn", "NIL"),
      patientPref.getString("travel", "NIL"),
      caregiverPref.getString("fullName", "NIL"),
      caregiverPref.getString("relationship", "NIL"),
      caregiverPref.getString("location", "NIL"),
      caregiverPref.getString("frequency", "NIL"),
      patientPref.getInt("AdlsScore", -1),
      patientPref.getInt("adls_q1", -1),
      patientPref.getInt("adls_q2", -1),
      patientPref.getInt("adls_q3", -1),
      patientPref.getInt("adls_q4", -1),
      patientPref.getInt("adls_q5", -1),
      patientPref.getInt("adls_q6", -1),
      patientPref.getInt("BriefScore", -1),
      patientPref.getInt("brief_q1", -1),
      patientPref.getInt("brief_q2", -1),
      patientPref.getInt("brief_q3", -1),
      patientPref.getInt("brief_q4", -1),
      patientPref.getInt("ContextScore1", -1),
      patientPref.getInt("ContextScore2", -1),
      patientPref.getInt("ContextScore3", -1),
      patientPref.getInt("IADLSScore", -1),
      patientPref.getInt("iadls_q1", -1),
      patientPref.getInt("iadls_q2", -1),
      patientPref.getInt("iadls_q3", -1),
      patientPref.getInt("iadls_q4", -1),
      patientPref.getInt("iadls_q5", -1),
      patientPref.getInt("iadls_q6", -1),
      patientPref.getInt("iadls_q7", -1),
      patientPref.getInt("iadls_q8", -1),
      patientPref.getInt("LonelinessScaleScore", -1),
      patientPref.getInt("loneliness_q1", -1),
      patientPref.getInt("loneliness_q2", -1),
      patientPref.getInt("loneliness_q3", -1),
      patientPref.getInt("LSNS6Score", -1),
      patientPref.getInt("lsns_q1", -1),
      patientPref.getInt("lsns_q2", -1),
      patientPref.getInt("lsns_q3", -1),
      patientPref.getInt("lsns_q4", -1),
      patientPref.getInt("lsns_q5", -1),
      patientPref.getInt("lsns_q6", -1),
      patientPref.getInt("PseqScore", -1),
      patientPref.getInt("pseq_q1", -1),
      patientPref.getInt("pseq_q2", -1),
      patientPref.getInt("pseq_q3", -1),
      patientPref.getInt("pseq_q4", -1),
      patientPref.getInt("pseq_q5", -1),
      patientPref.getInt("pseq_q6", -1),
      patientPref.getInt("pseq_q7", -1),
      patientPref.getInt("pseq_q8", -1),
      patientPref.getInt("pseq_q9", -1),
      patientPref.getInt("pseq_q10", -1),
      patientPref.getInt("Phq2Score", -1),
      patientPref.getInt("phq2_q1", -1),
      patientPref.getInt("phq2_q2", -1),
      patientPref.getInt("SRIScore", -1),
      patientPref.getInt("sri_q1", -1),
      patientPref.getInt("sri_q2", -1),
      patientPref.getInt("sri_q3", -1),
      patientPref.getInt("StaiScore", -1),
      patientPref.getInt("stai_q1", -1),
      patientPref.getInt("stai_q2", -1),
      patientPref.getInt("stai_q3", -1),
      patientPref.getInt("stai_q4", -1),
      patientPref.getInt("stai_q5", -1),
      patientPref.getInt("stai_q6", -1),
      patientPref.getInt("ThoughtsAboutPainScore", -1),
      patientPref.getInt("thoughtsAboutPain_q1", -1),
      patientPref.getInt("thoughtsAboutPain_q2", -1),
      patientPref.getInt("thoughtsAboutPain_q3", -1),
      patientPref.getInt("thoughtsAboutPain_q4", -1),
      patientPref.getInt("thoughtsAboutPain_q5", -1),
      patientPref.getInt("thoughtsAboutPain_q6", -1),
      patientPref.getInt("thoughtsAboutPain_q7", -1),
      patientPref.getInt("thoughtsAboutPain_q8", -1),
      patientPref.getInt("thoughtsAboutPain_q9", -1),
      patientPref.getInt("thoughtsAboutPain_q10", -1),
      patientPref.getInt("thoughtsAboutPain_q11", -1),
      patientPref.getInt("thoughtsAboutPain_q12", -1),
      patientPref.getInt("thoughtsAboutPain_q13", -1),
      patientPref.getString("champs_q1.1", "NIL"),
      patientPref.getString("champs_q1.2", "NIL"),
      patientPref.getString("champs_q2.1", "NIL"),
      patientPref.getString("champs_q2.2", "NIL"),
      patientPref.getString("champs_q3.1", "NIL"),
      patientPref.getString("champs_q3.2", "NIL"),
      patientPref.getString("champs_q4.1", "NIL"),
      patientPref.getString("champs_q4.2", "NIL"),
      patientPref.getString("champs_q5.1", "NIL"),
      patientPref.getString("champs_q5.2", "NIL"),
      patientPref.getString("champs_q6.1", "NIL"),
      patientPref.getString("champs_q6.2", "NIL"),
      patientPref.getString("champs_q7.1", "NIL"),
      patientPref.getString("champs_q7.2", "NIL"),
      patientPref.getString("champs_q8.1", "NIL"),
      patientPref.getString("champs_q8.2", "NIL"),
      patientPref.getString("champs_q9.1", "NIL"),
      patientPref.getString("champs_q9.2", "NIL"),
      patientPref.getString("champs_q10.1", "NIL"),
      patientPref.getString("champs_q10.2", "NIL"),
      patientPref.getString("champs_q11.1", "NIL"),
      patientPref.getString("champs_q11.2", "NIL"),
      patientPref.getString("champs_q12.1", "NIL"),
      patientPref.getString("champs_q12.2", "NIL"),
      patientPref.getString("champs_q13.1", "NIL"),
      patientPref.getString("champs_q13.2", "NIL"),
      patientPref.getString("champs_q14.1", "NIL"),
      patientPref.getString("champs_q14.2", "NIL"),
      patientPref.getString("champs_q15.1", "NIL"),
      patientPref.getString("champs_q15.2", "NIL"),
      patientPref.getString("champs_q16.1", "NIL"),
      patientPref.getString("champs_q16.2", "NIL"),
      patientPref.getString("champs_q17.1", "NIL"),
      patientPref.getString("champs_q17.2", "NIL"),
      patientPref.getString("champs_q18.1", "NIL"),
      patientPref.getString("champs_q18.2", "NIL"),
      patientPref.getString("champs_q19.1", "NIL"),
      patientPref.getString("champs_q19.2", "NIL"),
      patientPref.getString("champs_q20.1", "NIL"),
      patientPref.getString("champs_q20.2", "NIL"),
      patientPref.getString("champs_q21.1", "NIL"),
      patientPref.getString("champs_q21.2", "NIL"),
      patientPref.getString("champs_q22.1", "NIL"),
      patientPref.getString("champs_q22.2", "NIL"),
      patientPref.getString("champs_q23.1", "NIL"),
      patientPref.getString("champs_q23.2", "NIL"),
      patientPref.getString("champs_q24.1", "NIL"),
      patientPref.getString("champs_q24.2", "NIL"),
      patientPref.getString("champs_q25.1", "NIL"),
      patientPref.getString("champs_q25.2", "NIL"),
      patientPref.getString("champs_q26.1", "NIL"),
      patientPref.getString("champs_q26.2", "NIL"),
      patientPref.getString("champs_q27.1", "NIL"),
      patientPref.getString("champs_q27.2", "NIL"),
      patientPref.getString("champs_q28.1", "NIL"),
      patientPref.getString("champs_q28.2", "NIL"),
      patientPref.getString("champs_q29.1", "NIL"),
      patientPref.getString("champs_q29.2", "NIL"),
      patientPref.getString("champs_q30.1", "NIL"),
      patientPref.getString("champs_q30.2", "NIL"),
      patientPref.getString("champs_q31.1", "NIL"),
      patientPref.getString("champs_q31.2", "NIL"),
      patientPref.getString("champs_q32.1", "NIL"),
      patientPref.getString("champs_q32.2", "NIL"),
      patientPref.getString("champs_q33.1", "NIL"),
      patientPref.getString("champs_q33.2", "NIL"),
      patientPref.getString("champs_q34.1", "NIL"),
      patientPref.getString("champs_q34.2", "NIL"),
      patientPref.getString("champs_q35.1", "NIL"),
      patientPref.getString("champs_q35.2", "NIL"),
      patientPref.getString("champs_q36.1", "NIL"),
      patientPref.getString("champs_q36.2", "NIL"),
      patientPref.getString("champs_q37.1", "NIL"),
      patientPref.getString("champs_q37.2", "NIL"),
      patientPref.getString("champs_q38.1", "NIL"),
      patientPref.getString("champs_q38.2", "NIL"),
      patientPref.getString("champs_q39.1", "NIL"),
      patientPref.getString("champs_q39.2", "NIL"),
      patientPref.getString("champs_q40.1", "NIL"),
      patientPref.getString("champs_q40.2", "NIL"),
      patientPref.getString("champs_q41.1", "NIL"),
      patientPref.getString("champs_q41.2", "NIL"),
      patientPref.getString("openEndedQuestionnaireQ1", "NIL"),
      patientPref.getString("openEndedQuestionnaireQ2", "NIL"),
      patientPref.getString("openEndedQuestionnaireQ3", "NIL"),
      patientPref.getString("openEndedQuestionnaireQ4", "NIL"),
      patientPref.getString("openEndedQuestionnaireQ5", "NIL"),
      patientPref.getString("openEndedQuestionnaireQ6", "NIL"),
      caregiverPref.getInt("CBSScore", -1),
      caregiverPref.getInt("cbs_q1", -1),
      caregiverPref.getInt("cbs_q2", -1),
      caregiverPref.getInt("cbs_q3", -1),
      caregiverPref.getInt("cbs_q4", -1),
      caregiverPref.getInt("cbs_q5", -1),
      caregiverPref.getInt("cbs_q6", -1),
      caregiverPref.getInt("cbs_q7", -1),
      caregiverPref.getInt("cbs_q8", -1),
      caregiverPref.getInt("cbs_q9", -1),
      caregiverPref.getInt("cbs_q10", -1),
      caregiverPref.getInt("cbs_q11", -1),
      caregiverPref.getInt("cbs_q12", -1),
      caregiverPref.getInt("cbs_q13", -1),
      caregiverPref.getInt("cbs_q14", -1),
      caregiverPref.getInt("cbs_q15", -1),
      caregiverPref.getInt("cbs_q16", -1),
      caregiverPref.getInt("cbs_q17", -1),
      caregiverPref.getInt("cbs_q18", -1),
      caregiverPref.getInt("cbs_q19", -1),
      caregiverPref.getInt("cbs_q20", -1),
      caregiverPref.getInt("cbs_q21", -1),
      caregiverPref.getInt("cbs_q22", -1),
      caregiverPref.getInt("AdlsScore", -1),
      caregiverPref.getInt("adls_q1", -1),
      caregiverPref.getInt("adls_q2", -1),
      caregiverPref.getInt("adls_q3", -1),
      caregiverPref.getInt("adls_q4", -1),
      caregiverPref.getInt("adls_q5", -1),
      caregiverPref.getInt("adls_q6", -1),
      caregiverPref.getInt("IADLSScore", -1),
      caregiverPref.getInt("iadls_q1", -1),
      caregiverPref.getInt("iadls_q2", -1),
      caregiverPref.getInt("iadls_q3", -1),
      caregiverPref.getInt("iadls_q4", -1),
      caregiverPref.getInt("iadls_q5", -1),
      caregiverPref.getInt("iadls_q6", -1),
      caregiverPref.getInt("iadls_q7", -1),
      caregiverPref.getInt("iadls_q8", -1),
      caregiverPref.getInt("SRIScore", -1),
      caregiverPref.getInt("sri_q1", -1),
      caregiverPref.getInt("sri_q2", -1),
      caregiverPref.getInt("sri_q3", -1),
    )

    csvPrinter.flush()
    csvPrinter.close()

    // Images
    exportImage("photo1", patientPref)
    exportImage("photo2", patientPref)
    exportImage("photo3", patientPref)
    exportImage("photo4", patientPref)
    exportImage("photo5", patientPref)
    exportImage("photo6", patientPref)
    exportImage("photo7", patientPref)
    exportImage("photo8", patientPref)
    exportImage("photo9", patientPref)
    exportImage("photo10", patientPref)
  }

  private fun exportImage(imageName: String, sharedPreferences: SharedPreferences) {
    val sourcePath = sharedPreferences.getString(imageName, "")
    if (sourcePath == "") return
    val fileName = sourcePath!!.split("/".toRegex()).last()
    val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val source = File(storageDir.toString() + File.separator + fileName)

    val destinationPath =
      Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath +
        imageName +
        ".png"
    val destination = File(destinationPath)
    try {
      Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING)
    } catch (e: IOException) {
      Log.d("Export", "Failed to export image: $imageName" + e.printStackTrace().toString())
    }
  }

  override fun onClick(v: View?) {
    if (v != null) {
      when (v.id) {}
    }
  }

  fun exportButton() {
    try {
      exportData()
    } catch (e: Exception) {
      val contextView = requireView()
      Snackbar.make(contextView, "Failed to export data.", Snackbar.LENGTH_SHORT).show()
      Log.d("Export", e.printStackTrace().toString())

      return
    }

    val contextView = requireView()
    Snackbar.make(
        contextView,
        "Data has been exported successfully to your documents folder.",
        Snackbar.LENGTH_LONG
      )
      .show()
  }
}

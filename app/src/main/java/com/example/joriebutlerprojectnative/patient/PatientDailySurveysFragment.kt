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
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.joriebutlerprojectnative.*
import com.example.joriebutlerprojectnative.dailySurveys.Gad2DailyFragment
import com.example.joriebutlerprojectnative.dailySurveys.LonelinessDailyFragment
import com.example.joriebutlerprojectnative.dailySurveys.MedicationDailyFragment
import com.example.joriebutlerprojectnative.dailySurveys.MobilityDailyFragment
import com.example.joriebutlerprojectnative.dailySurveys.Pcs3DailyFragment
import com.example.joriebutlerprojectnative.dailySurveys.Phq2DailyFragment
import com.example.joriebutlerprojectnative.dailySurveys.Pseq2DailyFragment
import com.example.joriebutlerprojectnative.dailySurveys.Zbi4DailyFragment
import com.example.joriebutlerprojectnative.databinding.FragmentPatientDailySurveysBinding
import com.example.joriebutlerprojectnative.surveys.*
import java.io.File
import java.text.DateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Date

/**
 * A simple [Fragment] subclass. Use the [PatientDailySurveysFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientDailySurveysFragment : Fragment(), OnClickListener {

  private var _binding: FragmentPatientDailySurveysBinding? = null
  private val binding
    get() = _binding!!

  private var uri: Uri? = null
  private var dailyPhotoLauncher: ActivityResultLauncher<Uri>? = null
  private var currentImagePath: String = ""
  val timeStamp: String = LocalDate.now().toString()


  fun createImageFile(filename: String): File {

    val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    return File.createTempFile(filename + "_" + timeStamp, ".png", storageDir).apply {
      currentImagePath = absolutePath
    }
  }

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
    _binding = FragmentPatientDailySurveysBinding.inflate(inflater, container, false)
    val rootView = _binding!!.root

    binding.buttonGAD2.setOnClickListener(this)
    binding.buttonDailyLoneliness.setOnClickListener(this)
//    binding.buttonPCS3.setOnClickListener(this)
    binding.buttonPHQ2.setOnClickListener(this)
//    binding.buttonPSEQ2.setOnClickListener(this)
    binding.buttonZBI4.setOnClickListener(this)
    binding.buttonMobility.setOnClickListener(this)
    binding.buttonMedication.setOnClickListener(this)

    // Check which questionnaires have been completed already.
    val patientSharedPref =
      requireActivity().getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)


    dailyPhotoLauncher =
      setupPhotoActivityResultLauncher("dailyPhoto_$timeStamp", binding.dailyPhotoImageView)

    checkCompletion(patientSharedPref, "Gad2DailyCompleted", binding.buttonGAD2)
    checkCompletion(patientSharedPref, "LonelinessDailyCompleted", binding.buttonDailyLoneliness)
//    checkCompletion(patientSharedPref, "Pcs3DailyCompleted", binding.buttonPCS3)
    checkCompletion(patientSharedPref, "Phq2DailyCompleted", binding.buttonPHQ2)
//    checkCompletion(patientSharedPref, "Pseq2DailyCompleted", binding.buttonPSEQ2)
    checkCompletion(patientSharedPref, "Zbi4DailyCompleted", binding.buttonZBI4)
    checkCompletion(patientSharedPref, "MobilityDailyCompleted", binding.buttonMobility)
    checkCompletion(patientSharedPref, "MedicationDailyCompleted", binding.buttonMedication)
    checkDailyPhotoCompletion(patientSharedPref, "dailyPhoto", binding.dailyPhotoButton)

    return rootView
  }

  override fun onStart() {
    super.onStart()
    binding.dailyPhotoButton.setOnClickListener(this)
    // Load the pictures that have already been taken.
    val sharedPref =
      requireActivity().getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)

    loadImage("dailyPhoto", binding.dailyPhotoImageView, sharedPref)
  }

  private fun checkDailyPhotoCompletion(sharedPref: SharedPreferences, string: String, button: Button) {
    val imagePath = sharedPref.getString(string + "_$timeStamp", "")

    if (!imagePath.isNullOrEmpty()) {
      button.isEnabled = false
    }
    else {
      button.isEnabled = true
    }
  }

  private fun checkCompletion(sharedPref: SharedPreferences, string: String, button: Button) {
    val completed = sharedPref.getInt(string, 0)
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val lastCompleted = sharedPref.getString(string + "Timestamp", "")

    var from = LocalDate.parse(LocalDate.now().toString(), dateFormatter)
    val to = LocalDate.parse(LocalDate.now().toString(), dateFormatter)
    if (!lastCompleted.isNullOrEmpty()) {
      from = LocalDate.parse(lastCompleted, dateFormatter)
    }
    val difference = Period.between(from, to).days

    Log.d(
      "CHECK DAILY SURVEYS",
      "SURVEY: $string\nSURVEY COMPLETED?: $completed\nTimestamp: $from\nCurrent Date: $to\nDifference in Days: $difference\n"
    )

    if (completed == 1 && from.toString() != "" && difference == 0) {
      button.isEnabled = false
      val img: Drawable =
        ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_check_24)!!
      button.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
      Log.d("CHECK DAILY SURVEYS", "Disabling the button for $string.")
    } else {
      button.isEnabled = true
      button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
      Log.d("CHECK DAILY SURVEYS", "Enabling the button for $string.")
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
        R.id.buttonGAD2 -> {

          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, Gad2DailyFragment())
            .commit()
        }
        R.id.buttonDailyLoneliness -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, LonelinessDailyFragment())
            .commit()
        }
//        R.id.buttonPCS3 -> {
//          parentFragmentManager
//            .beginTransaction()
//            .replace(R.id.constraintLayout, Pcs3DailyFragment())
//            .commit()
//        }
        R.id.buttonPHQ2 -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, Phq2DailyFragment())
            .commit()
        }
//        R.id.buttonPSEQ2 -> {
//          parentFragmentManager
//            .beginTransaction()
//            .replace(R.id.constraintLayout, Pseq2DailyFragment())
//            .commit()
//        }
        R.id.buttonZBI4 -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, Zbi4DailyFragment())
            .commit()
        }
        R.id.buttonMobility -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, MobilityDailyFragment())
            .commit()
        }
        R.id.buttonMedication -> {
          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, MedicationDailyFragment())
            .commit()
        }
        R.id.dailyPhotoButton -> {
          takePicture("dailyPhoto", dailyPhotoLauncher)
        }
      }
    }
  }

  private fun setupPhotoActivityResultLauncher(
    imageName: String,
    imageView: ImageView
  ): ActivityResultLauncher<Uri> {

    return (registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
      if (success) {
        Log.d("Capture Image", "Got image at: $uri")

        Glide.with(this).load(uri).centerInside().override(650).into(imageView)

        val sharedPref =
          requireActivity()
            .getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(imageName, uri.toString())
        editor.apply()
        checkDailyPhotoCompletion(sharedPref, "dailyPhoto", binding.dailyPhotoButton)

      }
    })
  }

  private fun takePicture(imageName: String, activityResultLauncher: ActivityResultLauncher<Uri>?) {
    uri =
      FileProvider.getUriForFile(
        requireContext(),
        "${BuildConfig.APPLICATION_ID}.fileprovider",
        createImageFile(imageName)
      )
    activityResultLauncher!!.launch(uri)

  }

  private fun loadImage(
    imageName: String,
    imageView: ImageView,
    sharedPreferences: SharedPreferences
  ) {
    try {
      Glide.with(this)
        .load(sharedPreferences.getString(imageName + "_$timeStamp", ""))
        .centerInside()
        .override(650)
        .into(imageView)
    } catch (e: Exception) {
      // Handling the null pointer exception
    }
  }
}

/*
 *     Created by Anirudh Lath in 2021
 *     anirudhlath@gmail.com
 *     Last modified 11/30/23, 6:38 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.patient

import android.content.Context
import android.net.Uri
import android.os.Build
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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.joriebutlerprojectnative.BuildConfig
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.FragmentPatientSignUpBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.File
import java.text.DateFormat.getDateInstance
import java.time.LocalDate
import java.util.*

/**
 * A simple [Fragment] subclass. Use the [PatientSignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientSignUpFragment : Fragment(), OnClickListener {

  private var fNameEditText: TextInputLayout? = null
  private var lNameEditText: TextInputLayout? = null
  private var dobEditText: TextInputEditText? = null
  private var mrnEditText: TextInputLayout? = null
  private var genderMenu: TextInputLayout? = null
  private var travelMenu: TextInputLayout? = null
  private var _binding: FragmentPatientSignUpBinding? = null
  private val binding
    get() = _binding!!

  private var currentImagePath: String = ""

  private var uri: Uri? = null

  private var getCameraImage: ActivityResultLauncher<Uri>? = null

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    uri =
      FileProvider.getUriForFile(
        requireContext(),
        "${BuildConfig.APPLICATION_ID}.fileprovider",
        createImageFile("patient_profile_picture")
      )

    getCameraImage =
      registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
          Log.d("Capture Image", "Got image at: $uri")

          binding.imageButton.setContentPadding(0, 0, 0, 0)

          Glide.with(this).load(uri).centerInside().override(200).into(binding.imageButton)

          val sharedPref =
            requireActivity()
              .getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)
          val editor = sharedPref.edit()
          editor.putString("profilePhotoURI", uri.toString())
          editor.apply()
        }
      }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentPatientSignUpBinding.inflate(inflater, container, false)
    val rootView = _binding!!.root

    val button = rootView.findViewById<Button>(R.id.buttonPatientFinishProfile)
    fNameEditText = rootView.findViewById(R.id.textFieldFirstName)
    lNameEditText = rootView.findViewById(R.id.textFieldLastName)
    dobEditText = rootView.findViewById(R.id.textFieldDob)
    mrnEditText = rootView.findViewById(R.id.textFieldMrn)
    genderMenu = rootView.findViewById(R.id.menuGender)
    travelMenu = rootView.findViewById(R.id.menuTravel)

    val imageButton = binding.imageButton

    fNameEditText?.setOnClickListener(this)
    lNameEditText?.setOnClickListener(this)
    dobEditText?.setOnClickListener(this)
    mrnEditText?.setOnClickListener(this)
    genderMenu?.setOnClickListener(this)
    travelMenu?.setOnClickListener(this)
    button?.setOnClickListener(this)
    imageButton.setOnClickListener(this)

    return rootView
  }

  // Destroy binding at fragment destroy
  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  // Create image file
  fun createImageFile(filename: String): File {

    val timeStamp: String = LocalDate.now().toString()
    val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    return File.createTempFile(filename + timeStamp, ".png", storageDir).apply {
      currentImagePath = absolutePath
    }
  }

  /**
   * Called when a view has been clicked.
   *
   * @param v The view that was clicked.
   */
  override fun onClick(v: View?) {
    if (v != null) {
      when (v.id) {
        R.id.imageButton -> {
          uri =
            FileProvider.getUriForFile(
              requireContext(),
              "${BuildConfig.APPLICATION_ID}.fileprovider",
              createImageFile("patient_profile_picture")
            )

          getCameraImage!!.launch(uri)

          return
        }
        R.id.buttonPatientFinishProfile -> {
          Log.d("UI", "Profile finish button clicked.")
          val sharedPref =
            requireActivity()
              .getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)

          // Save data
          if (
            !fNameEditText?.editText?.text.isNullOrEmpty() &&
              !lNameEditText?.editText?.text.isNullOrEmpty() &&
              !dobEditText?.text.isNullOrEmpty() &&
              !mrnEditText?.editText?.text.isNullOrEmpty() &&
              mrnEditText?.editText?.text.toString().trim().length == 4 &&
              !genderMenu?.editText?.text.isNullOrEmpty() &&
              !travelMenu?.editText?.text.isNullOrEmpty()
          ) {
            Log.d("SharedPreferences", "Trying to save data save data...")
            Log.d(
              "SharedPreferences",
              "First Name      : " + fNameEditText?.editText?.text.toString()
            )
            Log.d(
              "SharedPreferences",
              "Last Name       : " + lNameEditText?.editText?.text.toString()
            )
            Log.d("SharedPreferences", "Date of Birth   : " + dobEditText?.text.toString())
            Log.d(
              "SharedPreferences",
              "MRN             : " + mrnEditText?.editText?.text.toString()
            )
            Log.d("SharedPreferences", "Gender          : " + genderMenu?.editText?.text.toString())
            Log.d("SharedPreferences", "Travel          : " + travelMenu?.editText?.text.toString())

            val editor = sharedPref.edit()
            editor.putString("fName", fNameEditText?.editText?.text.toString().trim())
            editor.putString("lName", lNameEditText?.editText?.text.toString().trim())
            editor.putString("dob", dobEditText?.text.toString())
            editor.putString("mrn", mrnEditText?.editText?.text.toString())
            editor.putString("gender", genderMenu?.editText?.text.toString())
            editor.putString("travel", travelMenu?.editText?.text.toString())
            editor.apply()

            if (sharedPref.getString("profilePhotoURI", "").isNullOrEmpty()) {
              val contextView = requireView()
              Snackbar.make(contextView, "Please add a profile picture.", Snackbar.LENGTH_SHORT)
                .show()
              return
            }
          } else {
            val contextView = requireView()
            Snackbar.make(contextView, "Please complete all the fields.", Snackbar.LENGTH_SHORT)
              .show()
            return
          }

          // Debug Logs and confirm if the data was saved
          Log.d("SharedPreferences", "Loading the save data...")
          Log.d(
            "SharedPreferences",
            "First Name      : " + sharedPref.getString("fName", "NIL").toString()
          )
          Log.d(
            "SharedPreferences",
            "Last Name       : " + sharedPref.getString("lName", "NIL").toString()
          )
          Log.d(
            "SharedPreferences",
            "Date of Birth   : " + sharedPref.getString("dob", "NIL").toString()
          )
          Log.d(
            "SharedPreferences",
            "MRN             : " + sharedPref.getString("mrn", "NIL").toString()
          )
          Log.d(
            "SharedPreferences",
            "Gender          : " + sharedPref.getString("gender", "NIL").toString()
          )
          Log.d(
            "SharedPreferences",
            "Travel          : " + sharedPref.getString("travel", "NIL").toString()
          )

          parentFragmentManager
            .beginTransaction()
            .replace(R.id.constraintLayout, PatientHomePageFragment())
            .commit()

          return
        }
        R.id.textFieldFirstName -> {}
        R.id.textFieldLastName -> {}
        R.id.textFieldDob -> {
          Log.d("OnClick", "Date Picker init")
          val datePicker =
            MaterialDatePicker.Builder.datePicker()
              .setTitleText("Select your birth date")
              .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
              .build()

          datePicker.show(parentFragmentManager, "DOB_picker")
          datePicker.addOnPositiveButtonClickListener {
            dobEditText?.setText(datePicker.headerText)
          }

          return
        }
        R.id.textFieldMrn -> {}
        R.id.menuGender -> {}
        R.id.menuTravel -> {}
      }
    }
  }

  override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
    return if (enter) {
      AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
    } else {
      AnimationUtils.loadAnimation(activity, R.anim.fade_out)
    }
  }
}

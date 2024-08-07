/*
 *     Created by Anirudh Lath in 2021
 *     anirudhlath@gmail.com
 *     Last modified 11/30/23, 6:38 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.patient

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.joriebutlerprojectnative.R

class PatientActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_patient)

    val sharedPref = getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)
    if (!sharedPref.contains("fName")) {
      supportFragmentManager
        .beginTransaction()
        .replace(R.id.constraintLayout, PatientSignUpFragment())
        .commit()
    } else {
      supportFragmentManager
        .beginTransaction()
        .replace(R.id.constraintLayout, PatientHomePageFragment())
        .commit()
    }
  }
}

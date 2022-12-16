/*
 * *
 *  * Created by Anirudh Lath on 12/16/22, 11:37 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 12/16/22, 11:37 AM
 *
 */

package com.example.joriebutlerprojectnative.patient

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.joriebutlerprojectnative.R

class PatientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)

        val sharedPref = getSharedPreferences(
            getString(R.string.patientData), Context.MODE_PRIVATE
        )
        if (!sharedPref.contains("fName")) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.constraintLayout, PatientSignUpFragment()).commit()
        }
        else {
            supportFragmentManager.beginTransaction().replace(R.id.constraintLayout, PatientHomePageFragment()).commit()
        }




    }


}
/*
 *     Created by Anirudh Lath on 4/4/23, 8:40 PM
 *     anirudhlath@gmail.com
 *     Last modified 4/4/23, 8:40 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.caregiver

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.joriebutlerprojectnative.R

class CaregiverActivity : AppCompatActivity() {
    private lateinit var attributionContext: Context


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caregiver)
        attributionContext = createAttributionContext("caregiver")


        val sharedPref = getSharedPreferences(
            getString(R.string.caregiverData), Context.MODE_PRIVATE
        )
        if (!sharedPref.contains("fullName")) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, CaregiverSignUpFragment() ).commit()
        }
        else {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2, CaregiverHomePageFragment()).commit()
        }

    }

}
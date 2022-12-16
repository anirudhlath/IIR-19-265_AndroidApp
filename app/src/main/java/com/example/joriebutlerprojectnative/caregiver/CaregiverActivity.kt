/*
 *     Created by Anirudh Lath on 12/16/22, 11:44 AM
 *     anirudhlath@gmail.com
 *     Last modified 12/16/22, 11:43 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.caregiver

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
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
/*
 *     Created by Anirudh Lath on 12/16/22, 11:44 AM
 *     anirudhlath@gmail.com
 *     Last modified 12/16/22, 11:43 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.joriebutlerprojectnative.caregiver.CaregiverActivity
import com.example.joriebutlerprojectnative.careprovider.CareproviderActivity
import com.example.joriebutlerprojectnative.databinding.ActivityMainBinding
import com.example.joriebutlerprojectnative.patient.PatientActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (!hasCameraPermission() || hasStoragePermission() || hasReadStoragePermission()) {
            requestMultiplePermissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA
                )
            )
        }
    }

    private val requestMultiplePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { resultsMap ->
        resultsMap.forEach {
            Log.i(ContentValues.TAG, "Permission: ${it.key}, granted: ${it.value}")
        }
    }

    // Check if device has permissions
    private fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        applicationContext,
        android.Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun hasStoragePermission() = ContextCompat.checkSelfPermission(
        applicationContext,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
    private fun hasReadStoragePermission() = ContextCompat.checkSelfPermission(
        applicationContext,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    fun goToPatientActivity(view: View) {
        startActivity(Intent(this@MainActivity, PatientActivity::class.java))
    }

    fun goToCaregiverActivity(view: View) {
        startActivity(Intent(this@MainActivity, CaregiverActivity::class.java))
    }

    fun goToCareproviderActivity(view: View) {
        startActivity(Intent(this@MainActivity, CareproviderActivity::class.java))
    }
}
package com.example.joriebutlerprojectnative

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.joriebutlerprojectnative.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun goToPatientActivity(view: View) {
        startActivity(Intent(this@MainActivity, PatientActivity::class.java))
    }

    fun goToCaregiverActivity(view: View) {
        startActivity(Intent(this@MainActivity, CaregiverActivity::class.java))
    }
}
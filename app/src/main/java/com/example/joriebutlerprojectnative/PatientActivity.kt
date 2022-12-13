package com.example.joriebutlerprojectnative

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

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
package com.example.joriebutlerprojectnative

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class PatientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)
        supportFragmentManager.beginTransaction()
            .replace(R.id.constraintLayout, PatientSignUpFragment()).commit()


    }

}
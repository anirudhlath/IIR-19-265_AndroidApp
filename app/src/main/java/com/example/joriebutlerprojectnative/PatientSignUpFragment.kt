package com.example.joriebutlerprojectnative

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.databinding.ActivityMainBinding
import com.example.joriebutlerprojectnative.databinding.ActivityPatientBinding


/**
 * A simple [Fragment] subclass.
 * Use the [PatientSignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientSignUpFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_sign_up, container, false)
    }

    fun finishSetup(view: View) {
        val fragment = PatientHomePageFragment()
        parentFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment)

    }


}
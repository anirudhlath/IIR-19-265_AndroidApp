package com.example.joriebutlerprojectnative

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment


/**
 * A simple [Fragment] subclass.
 * Use the [PatientSignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientSignUpFragment : Fragment(), OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_patient_sign_up, container, false)
        val button = rootView.findViewById<Button>(R.id.buttonPatientFinishProfile)
        button.setOnClickListener(this)
        return rootView
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.buttonPatientFinishProfile -> {
                    Log.d("UI", "Profile finish button clicked.")
//                    val fragment = PatientHomePageFragment()
                    parentFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, PatientHomePageFragment()).commit()
                }

            }
        }
    }


}
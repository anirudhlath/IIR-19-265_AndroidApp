package com.example.joriebutlerprojectnative.caregiver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment

/**
 * A simple [Fragment] subclass.
 * Use the [CaregiverHomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CaregiverHomePageFragment : Fragment(), OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_caregiver_home_page, container, false)
        rootView.findViewById<ImageButton>(R.id.surveysButton).setOnClickListener(this)

        return rootView
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        if(v != null) {
            when (v.id) {
                R.id.surveysButton -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView2, CaregiverSurveysFragment()).commit()
                }
            }
        }
    }

}
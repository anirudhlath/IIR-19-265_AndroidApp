package com.example.joriebutlerprojectnative.caregiver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.FragmentCaregiverSurveysBinding
import com.example.joriebutlerprojectnative.databinding.FragmentPatientSurveysBinding
import com.example.joriebutlerprojectnative.patient.PatientHomePageFragment
import com.example.joriebutlerprojectnative.surveys.CaregiverBurdenScaleFragment


/**
 * A simple [Fragment] subclass.
 * Use the [CaregiverSurveysFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CaregiverSurveysFragment : Fragment() {
    private var _binding: FragmentCaregiverSurveysBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView2, CaregiverHomePageFragment()).commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCaregiverSurveysBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        binding.buttonCBS.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, CaregiverBurdenScaleFragment()).commit()
        }

        return rootView
    }


}
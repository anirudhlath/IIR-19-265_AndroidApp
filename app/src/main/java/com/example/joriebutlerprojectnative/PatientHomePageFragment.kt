package com.example.joriebutlerprojectnative

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.example.joriebutlerprojectnative.databinding.FragmentPatientHomePageBinding
import com.example.joriebutlerprojectnative.databinding.FragmentPatientImageSurveyBinding
import com.example.joriebutlerprojectnative.databinding.FragmentPatientSignUpBinding

/**
 * A simple [Fragment] subclass.
 * Use the [PatientHomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientHomePageFragment : Fragment(), OnClickListener {

    private var _binding: FragmentPatientHomePageBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPatientHomePageBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        val imagesButton = binding.imagesButton

        imagesButton.setOnClickListener(this)
        binding.surveysButton.setOnClickListener(this)
        binding.openEndedQuestionsButton.setOnClickListener(this)


        return rootView
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.imagesButton -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, PatientImageSurveyFragment()).commit()

                    return
                }

                R.id.surveysButton -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, PatientSurveysFragment()).commit()

                    return
                }

                R.id.openEndedQuestionsButton -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, PatientOpenEndedQuestionsFragment()).commit()

                    return
                }
            }
        }
    }

}
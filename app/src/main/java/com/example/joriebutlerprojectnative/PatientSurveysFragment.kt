package com.example.joriebutlerprojectnative

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.joriebutlerprojectnative.databinding.FragmentPatientHomePageBinding
import com.example.joriebutlerprojectnative.databinding.FragmentPatientSurveysBinding


/**
 * A simple [Fragment] subclass.
 * Use the [PatientSurveysFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientSurveysFragment : Fragment(), OnClickListener {

    private var _binding: FragmentPatientSurveysBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.constraintLayout, PatientHomePageFragment()).commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPatientSurveysBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        binding.buttonSTAI.setOnClickListener(this)

        return rootView
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.buttonSTAI -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, StateTraitAnxietyInventoryFragment()).commit()

                    return
                }

            }
        }
    }

}
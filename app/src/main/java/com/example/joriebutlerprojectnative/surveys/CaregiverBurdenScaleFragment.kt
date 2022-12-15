package com.example.joriebutlerprojectnative.surveys

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.caregiver.CaregiverSurveysFragment
import com.example.joriebutlerprojectnative.databinding.QuestionnaireCaregiverBurdenScaleBinding
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


/**
 * A simple [Fragment] subclass.
 * Use the [CaregiverBurdenScaleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CaregiverBurdenScaleFragment : Fragment() {
    private var _binding: QuestionnaireCaregiverBurdenScaleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            QuestionnaireCaregiverBurdenScaleBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        binding.buttonSubmitSurvey.setOnClickListener {
            submitSurvey()
        }


        return rootView
    }

    private fun calculateScore(textInputLayout: TextInputLayout): Int {
        when (textInputLayout.editText?.text.toString()) {
            "Never" -> {
                return 0
            }
            "Rarely" -> {
                return 1
            }
            "Sometimes" -> {
                return 2
            }
            "Frequently" -> {
                return 3
            }
            "Nearly Always" -> {
                return 4
            }
        }
        return 0
    }

    private fun submitSurvey() {
        if (!binding.q1CBS.editText?.text.isNullOrEmpty()
            && !binding.q2CBS.editText?.text.isNullOrEmpty()
            && !binding.q3CBS.editText?.text.isNullOrEmpty()
            && !binding.q4CBS.editText?.text.isNullOrEmpty()
            && !binding.q5CBS.editText?.text.isNullOrEmpty()
            && !binding.q6CBS.editText?.text.isNullOrEmpty()
            && !binding.q7CBS.editText?.text.isNullOrEmpty()
            && !binding.q8CBS.editText?.text.isNullOrEmpty()
            && !binding.q9CBS.editText?.text.isNullOrEmpty()
            && !binding.q10CBS.editText?.text.isNullOrEmpty()
            && !binding.q11CBS.editText?.text.isNullOrEmpty()
            && !binding.q12CBS.editText?.text.isNullOrEmpty()
            && !binding.q13CBS.editText?.text.isNullOrEmpty()
            && !binding.q14CBS.editText?.text.isNullOrEmpty()
            && !binding.q15CBS.editText?.text.isNullOrEmpty()
            && !binding.q16CBS.editText?.text.isNullOrEmpty()
            && !binding.q17CBS.editText?.text.isNullOrEmpty()
            && !binding.q18CBS.editText?.text.isNullOrEmpty()
            && !binding.q19CBS.editText?.text.isNullOrEmpty()
            && !binding.q20CBS.editText?.text.isNullOrEmpty()
            && !binding.q21CBS.editText?.text.isNullOrEmpty()
            && !binding.q22CBS.editText?.text.isNullOrEmpty()
        ) {
            val sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.caregiverData), Context.MODE_PRIVATE
            )

            val score =
                calculateScore(
                    binding.q1CBS
                ) + calculateScore(
                    binding.q2CBS
                ) + calculateScore(
                    binding.q3CBS
                ) + calculateScore(
                    binding.q4CBS
                ) + calculateScore(
                    binding.q5CBS
                ) + calculateScore(
                    binding.q6CBS
                ) + calculateScore(
                    binding.q7CBS
                ) + calculateScore(
                    binding.q8CBS
                ) + calculateScore(
                    binding.q9CBS
                ) + calculateScore(
                    binding.q10CBS
                ) + calculateScore(
                    binding.q11CBS
                ) + calculateScore(
                    binding.q12CBS
                ) + calculateScore(
                    binding.q13CBS
                ) + calculateScore(binding.q14CBS) +
                        calculateScore(binding.q15CBS) +
                        calculateScore(binding.q16CBS) +
                        calculateScore(binding.q17CBS) +
                        calculateScore(binding.q18CBS) +
                        calculateScore(binding.q19CBS) +
                        calculateScore(binding.q20CBS) +
                        calculateScore(binding.q21CBS) +
                        calculateScore(binding.q22CBS)

            val editor = sharedPref.edit()
            editor.putInt("CBSScore", score)
            editor.apply()

            Log.d("SharedPreferences", "Loading the save data...")
            Log.d(
                "SharedPreferences",
                "Caregiver Burden Scale Score: " + sharedPref.getInt("CBSScore", -1).toString()
            )
        } else {
            val contextView = requireView()
            Snackbar.make(
                contextView,
                "Please complete all the fields.",
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView2, CaregiverSurveysFragment()).commit()
        return

    }


}
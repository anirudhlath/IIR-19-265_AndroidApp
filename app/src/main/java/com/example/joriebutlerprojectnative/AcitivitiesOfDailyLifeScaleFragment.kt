package com.example.joriebutlerprojectnative

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.joriebutlerprojectnative.databinding.QuestionnaireAcitivitiesOfDailyLifeScaleBinding
import com.example.joriebutlerprojectnative.databinding.QuestionnaireLubbenSocialNetworkScaleBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


/**
 * A simple [Fragment] subclass.
 * Use the [AcitivitiesOfDailyLifeScaleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AcitivitiesOfDailyLifeScaleFragment : Fragment() {

    private var _binding: QuestionnaireAcitivitiesOfDailyLifeScaleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            QuestionnaireAcitivitiesOfDailyLifeScaleBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        binding.buttonSubmitSurvey.setOnClickListener {
            submitSurvey()
        }

        return rootView
    }

    private fun calculateScore(textInputLayout: TextInputLayout): Int {
        when (textInputLayout.editText?.text.toString()[0].toString()) {
            "a" -> {
                return 1
            }
            "b" -> {
                return 0
            }

        }
        return 0
    }

    private fun submitSurvey() {
        if(!binding.q1Adls.editText?.text.isNullOrEmpty()
            &&!binding.q2Adls.editText?.text.isNullOrEmpty()
            &&!binding.q3Adls.editText?.text.isNullOrEmpty()
            &&!binding.q4Adls.editText?.text.isNullOrEmpty()
            &&!binding.q5Adls.editText?.text.isNullOrEmpty()
            &&!binding.q6Adls.editText?.text.isNullOrEmpty()
        ) {
            val sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.patientData), Context.MODE_PRIVATE
            )

            val score = calculateScore(binding.q1Adls) +
                    calculateScore(binding.q2Adls) +
                    calculateScore(binding.q3Adls) +
                    calculateScore(binding.q4Adls) +
                    calculateScore(binding.q5Adls) +
                    calculateScore(binding.q6Adls)

            val editor = sharedPref.edit()
            editor.putInt("AdlsScore", score)
            editor.apply()

            Log.d("SharedPreferences", "Loading the save data...")
            Log.d(
                "SharedPreferences",
                "Adls Score: " + sharedPref.getInt("AdlsScore", -1).toString()
            )
        }
        else {
            val contextView = requireView()
            Snackbar.make(
                contextView,
                "Please complete all the fields.",
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.constraintLayout, PatientSurveysFragment()).commit()
        return

    }


}
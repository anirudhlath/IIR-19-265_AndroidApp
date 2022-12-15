package com.example.joriebutlerprojectnative

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.example.joriebutlerprojectnative.databinding.QuestionnairePhq2Binding
import com.example.joriebutlerprojectnative.databinding.QuestionnaireStateTraitAnxietyInventoryBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


/**
 * A simple [Fragment] subclass.
 * Use the [Phq2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Phq2Fragment : Fragment() {

    private var _binding: QuestionnairePhq2Binding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            QuestionnairePhq2Binding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        binding.buttonSubmitSurvey.setOnClickListener {
            submitSurvey()
        }


        return rootView
    }

    private fun calculateScore(textInputLayout: TextInputLayout): Int {
        when (textInputLayout.editText?.text.toString()) {
            "Not at all" -> {
                return 0
            }
            "Several days" -> {
                return 1
            }
            "More than half of the days" -> {
                return 2
            }
            "Nearly every day" -> {
                return 3
            }

        }
        return 0
    }

    private fun submitSurvey() {
        if(!binding.q1phq2.editText?.text.isNullOrEmpty()
            &&!binding.q2phq2.editText?.text.isNullOrEmpty()
        ) {
            val sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.patientData), Context.MODE_PRIVATE
            )

            val score = calculateScore(binding.q1phq2) + calculateScore(binding.q2phq2)

            val editor = sharedPref.edit()
            editor.putInt("Phq2Score", score)
            editor.apply()

            Log.d("SharedPreferences", "Loading the save data...")
            Log.d(
                "SharedPreferences",
                "PHQ2 Score: " + sharedPref.getInt("Phq2Score", -1).toString()
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
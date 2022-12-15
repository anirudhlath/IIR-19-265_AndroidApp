package com.example.joriebutlerprojectnative

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.joriebutlerprojectnative.databinding.FragmentPatientImageSurveyBinding
import com.example.joriebutlerprojectnative.databinding.FragmentPatientOpenEndedQuestionsBinding
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 * Use the [PatientOpenEndedQuestionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientOpenEndedQuestionsFragment : Fragment() {

    private var _binding: FragmentPatientOpenEndedQuestionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPatientOpenEndedQuestionsBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        binding.buttonSubmitSurvey.setOnClickListener {
            submitSurvey()
        }

        return rootView


    }

    private fun submitSurvey() {
        if (!binding.q1.editText?.text.isNullOrEmpty()
            && !binding.q2.editText?.text.isNullOrEmpty()
            && !binding.q3.editText?.text.isNullOrEmpty()
            && !binding.q4.editText?.text.isNullOrEmpty()
            && !binding.q5.editText?.text.isNullOrEmpty()
        ) {
            val sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.patientData), Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("openEndedQuestionnaireQ1", binding.q1.editText?.text.toString())
            editor.putString("openEndedQuestionnaireQ2", binding.q2.editText?.text.toString())
            editor.putString("openEndedQuestionnaireQ3", binding.q3.editText?.text.toString())
            editor.putString("openEndedQuestionnaireQ4", binding.q4.editText?.text.toString())
            editor.putString("openEndedQuestionnaireQ5", binding.q5.editText?.text.toString())
            editor.apply()
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
            .replace(R.id.constraintLayout, PatientHomePageFragment()).commit()
        return
    }
}
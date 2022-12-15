package com.example.joriebutlerprojectnative

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.joriebutlerprojectnative.databinding.QuestionnaireLonelinessBinding
import com.example.joriebutlerprojectnative.databinding.QuestionnaireLubbenSocialNetworkScaleBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


/**
 * A simple [Fragment] subclass.
 * Use the [LubbenSocialNetworkScaleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LubbenSocialNetworkScaleFragment : Fragment() {

    private var _binding: QuestionnaireLubbenSocialNetworkScaleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            QuestionnaireLubbenSocialNetworkScaleBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        binding.buttonSubmitSurvey.setOnClickListener {
            submitSurvey()
        }


        return rootView
    }

    private fun calculateScore(textInputLayout: TextInputLayout): Int {
        when (textInputLayout.editText?.text.toString()) {
            "None" -> {
                return 0
            }
            "1" -> {
                return 1
            }
            "2" -> {
                return 2
            }
            "3 or 4" -> {
                return 3
            }
            "5 to 8" -> {
                return 4
            }
            "More than 9" -> {
                return 5
            }
        }
        return 0
    }

    private fun submitSurvey() {
        if(!binding.q1LSNS6.editText?.text.isNullOrEmpty()
            &&!binding.q2LSNS6.editText?.text.isNullOrEmpty()
            &&!binding.q3LSNS6.editText?.text.isNullOrEmpty()
            &&!binding.q4LSNS6.editText?.text.isNullOrEmpty()
            &&!binding.q5LSNS6.editText?.text.isNullOrEmpty()
            &&!binding.q6LSNS6.editText?.text.isNullOrEmpty()
        ) {
            val sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.patientData), Context.MODE_PRIVATE
            )

            val score = calculateScore(binding.q1LSNS6) +
                    calculateScore(binding.q2LSNS6) +
                    calculateScore(binding.q3LSNS6) +
                    calculateScore(binding.q4LSNS6) +
                    calculateScore(binding.q5LSNS6) +
                    calculateScore(binding.q6LSNS6)

            val editor = sharedPref.edit()
            editor.putInt("LSNS6Score", score)
            editor.apply()

            Log.d("SharedPreferences", "Loading the save data...")
            Log.d(
                "SharedPreferences",
                "LSNS6 Score: " + sharedPref.getInt("LSNS6Score", -1).toString()
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
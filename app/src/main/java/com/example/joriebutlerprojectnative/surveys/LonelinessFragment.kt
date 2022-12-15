package com.example.joriebutlerprojectnative.surveys

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.QuestionnaireLonelinessBinding
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

/**
 * A simple [Fragment] subclass.
 * Use the [LonelinessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LonelinessFragment : Fragment() {

    private var _binding: QuestionnaireLonelinessBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            QuestionnaireLonelinessBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        binding.buttonSubmitSurvey.setOnClickListener {
            submitSurvey()
        }


        return rootView
    }

    private fun calculateScore(textInputLayout: TextInputLayout): Int {
        when (textInputLayout.editText?.text.toString()) {
            "Hardly Ever" -> {
                return 1
            }
            "Some of the time" -> {
                return 2
            }
            "Often" -> {
                return 3
            }
        }
        return 0
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.fade_out)
        }
    }

    private fun submitSurvey() {
        if(!binding.q1LonelinessScale.editText?.text.isNullOrEmpty()
            &&!binding.q2LonelinessScale.editText?.text.isNullOrEmpty()
            &&!binding.q3LonelinessScale.editText?.text.isNullOrEmpty()
        ) {
            val sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.patientData), Context.MODE_PRIVATE
            )

            val score = calculateScore(binding.q1LonelinessScale) +
                    calculateScore(binding.q2LonelinessScale) +
                    calculateScore(binding.q3LonelinessScale)

            val editor = sharedPref.edit()
            editor.putInt("LonelinessScaleScore", score)
            editor.apply()

            Log.d("SharedPreferences", "Loading the save data...")
            Log.d(
                "SharedPreferences",
                "Loneliness Scale Score: " + sharedPref.getInt("LonelinessScaleScore", -1).toString()
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
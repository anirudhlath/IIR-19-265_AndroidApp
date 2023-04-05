/*
 *     Created by Anirudh Lath on 4/4/23, 8:40 PM
 *     anirudhlath@gmail.com
 *     Last modified 4/4/23, 8:40 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.surveys

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.caregiver.CaregiverSurveysFragment
import com.example.joriebutlerprojectnative.databinding.QuestionnaireCaregiverBurdenScaleBinding
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
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.fade_out)
        }
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
            editor.putInt("cbs_q1", calculateScore(binding.q1CBS))
            editor.putInt("cbs_q2", calculateScore(binding.q2CBS))
            editor.putInt("cbs_q3", calculateScore(binding.q3CBS))
            editor.putInt("cbs_q4", calculateScore(binding.q4CBS))
            editor.putInt("cbs_q5", calculateScore(binding.q5CBS))
            editor.putInt("cbs_q6", calculateScore(binding.q6CBS))
            editor.putInt("cbs_q7", calculateScore(binding.q7CBS))
            editor.putInt("cbs_q8", calculateScore(binding.q8CBS))
            editor.putInt("cbs_q9", calculateScore(binding.q9CBS))
            editor.putInt("cbs_q10", calculateScore(binding.q10CBS))
            editor.putInt("cbs_q11", calculateScore(binding.q11CBS))
            editor.putInt("cbs_q12", calculateScore(binding.q12CBS))
            editor.putInt("cbs_q13", calculateScore(binding.q13CBS))
            editor.putInt("cbs_q14", calculateScore(binding.q14CBS))
            editor.putInt("cbs_q15", calculateScore(binding.q15CBS))
            editor.putInt("cbs_q16", calculateScore(binding.q16CBS))
            editor.putInt("cbs_q17", calculateScore(binding.q17CBS))
            editor.putInt("cbs_q18", calculateScore(binding.q18CBS))
            editor.putInt("cbs_q19", calculateScore(binding.q19CBS))
            editor.putInt("cbs_q20", calculateScore(binding.q20CBS))
            editor.putInt("cbs_q21", calculateScore(binding.q21CBS))
            editor.putInt("cbs_q22", calculateScore(binding.q22CBS))
            editor.putInt("CBSCompleted", 1)
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
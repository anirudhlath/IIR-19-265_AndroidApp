package com.example.joriebutlerprojectnative.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import com.example.joriebutlerprojectnative.*
import com.example.joriebutlerprojectnative.databinding.FragmentPatientSurveysBinding
import com.example.joriebutlerprojectnative.surveys.*


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
        binding.buttonPHQ.setOnClickListener(this)
        binding.buttonPSEQ.setOnClickListener(this)
        binding.buttonThoughtsAboutPain.setOnClickListener(this)
        binding.buttonLoneliness.setOnClickListener(this)
        binding.buttonIsolation.setOnClickListener(this)
        binding.buttonActivitiesOfDailyLifeScale.setOnClickListener(this)
        binding.buttonInstrumentalActivitiesOfDailyLifeScale.setOnClickListener(this)
        binding.buttonContext.setOnClickListener(this)
        binding.buttonHealthLiteracy.setOnClickListener(this)

        return rootView
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.fade_out)
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.buttonSTAI -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, StateTraitAnxietyInventoryFragment())
                        .commit()

                    return
                }

                R.id.buttonPHQ -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, Phq2Fragment()).commit()

                    return
                }

                R.id.buttonPSEQ -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, PainSelfEfficacyQuestionnaireFragment()).commit()

                    return
                }
                R.id.buttonThoughtsAboutPain -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, ThoughtsAboutPainFragment()).commit()

                    return
                }
                R.id.buttonLoneliness -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, LonelinessFragment()).commit()

                    return
                }
                R.id.buttonIsolation -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, LubbenSocialNetworkScaleFragment()).commit()

                    return
                }
                R.id.buttonActivitiesOfDailyLifeScale -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.constraintLayout, AcitivitiesOfDailyLifeScaleFragment()).commit()

                    return
                }
                // TODO Patient Caregiver Relationship
                R.id.buttonInstrumentalActivitiesOfDailyLifeScale -> {
                    // TODO
//                    parentFragmentManager.beginTransaction()
//                        .replace(R.id.constraintLayout, Phq2Fragment()).commit()

                    return
                }
                R.id.buttonContext -> {
                    // TODO
//                    parentFragmentManager.beginTransaction()
//                        .replace(R.id.constraintLayout, Phq2Fragment()).commit()

                    return
                }
                R.id.buttonHealthLiteracy -> {
                    // TODO
//                    parentFragmentManager.beginTransaction()
//                        .replace(R.id.constraintLayout, Phq2Fragment()).commit()

                    return
                }

            }
        }
    }

}
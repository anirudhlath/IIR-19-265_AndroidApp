/*
 *     Created by Anirudh Lath on 12/16/22, 11:44 AM
 *     anirudhlath@gmail.com
 *     Last modified 12/16/22, 11:43 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.careprovider

import android.animation.ObjectAnimator
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.joriebutlerprojectnative.ImageSliderAdapter
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.SliderItem
import com.example.joriebutlerprojectnative.databinding.FragmentCareproviderReviewBinding
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [CareproviderReviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CareproviderReviewFragment : Fragment(), OnClickListener {

    private var _binding: FragmentCareproviderReviewBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentCareproviderReviewBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root


        prepareReviewPage()



        return rootView
    }

    public fun prepareReviewPage() {
        GlobalScope.launch {
            val sharedPref = requireActivity().getSharedPreferences(
                getString(R.string.patientData), Context.MODE_PRIVATE
            )
            updatePatientData(sharedPref)
            updateCaregiverData()
            updateAnxietyProgressBar(sharedPref)
            updateDepressionProgressBar(sharedPref)
            updateSelfEfficacyProgressBar(sharedPref)
            updateThoughtsAboutPainProgressBar(sharedPref)
            updateLonelinessScoreProgressBar(sharedPref)
            updateSocialIsolationProgressBar(sharedPref)
            updateDifficultyGettingMedicationProgressBar(sharedPref)
            updateDifficultyAffordingCareProgressBar(sharedPref)
            updateDifficultyGettingToClinicProgressBar(sharedPref)
            updateAdlsProgressBar()
            updateHealthLiteracyProgressBar(sharedPref)
            updateOpenEndedQuestions(sharedPref)
            updateImageSlider(sharedPref)
            updateCaregiverBurdenScaleProgressBar()
            updateIadlsProgressBar()
            updatePatientCaregiverRelationshipProgressBar()
            requireActivity().runOnUiThread {
//                requireActivity().findViewById<LinearProgressIndicator>(R.id.caregiverLoadingBar).visibility =
//                    View.INVISIBLE
            }
        }
    }


    private fun updateImageSlider(sharedPref: SharedPreferences) {
        val sliderView = binding.imageSlider
        val adapter = ImageSliderAdapter(requireContext())
        val labelArray = arrayOf(
            "What matters the most to me",
            "Where I keep my medicine",
            "Front of my house",
            "Second entry door",
            "Bedroom #1",
            "Bedroom #2",
            "My kitchen",
            "My bathroom",
            "Medical Equipment #1",
            "Medical Equipment #2"
        )

        // Add images to slider
        for (i in 1..10) {
            if (!sharedPref.getString("photo$i", "").isNullOrEmpty()) {
                adapter.addItem(SliderItem(labelArray[i - 1], sharedPref.getString("photo$i", "")))
                binding.imageIndicator.visibility = View.INVISIBLE
            }
        }
        sliderView.setSliderAdapter(adapter)
        requireActivity().runOnUiThread {
            sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
        }
        sliderView.indicatorSelectedColor = Color.WHITE
        sliderView.indicatorUnselectedColor = Color.GRAY
        sliderView.scrollTimeInSec = 4
        sliderView.startAutoCycle()
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.fade_out)
        }
    }

    private fun updatePatientData(sharedPref: SharedPreferences) {
        requireActivity().runOnUiThread {
            loadImage(
                "profilePhotoURI",
                binding.imageView,
                sharedPref
            )
        }
        binding.patientFullNameLabel.text =
            sharedPref.getString("fName", "NIL") + " " + sharedPref.getString("lName", "NIL")
        binding.patientDobLabel.text = sharedPref.getString("dob", "NIL")
        binding.patientMrnLabel.text = sharedPref.getString("mrn", "NIL")
        binding.patientTravelLabel.text = sharedPref.getString("travel", "NIL")


    }

    private fun updateCaregiverData() {
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.caregiverData), Context.MODE_PRIVATE
        )
        binding.caregiverFullNameLabel.text = sharedPref.getString("fullName", "NIL")
        binding.caregiverRelationshipLabel.text = sharedPref.getString("relationship", "NIL")
        binding.caregiverLocationLabel.text = sharedPref.getString("location", "NIL")
        binding.caregiverFrequencyLabel.text = sharedPref.getString("frequency", "NIL")
    }

    private fun loadImage(
        imageName: String,
        imageView: ImageView,
        sharedPreferences: SharedPreferences
    ) {
        try {
//            Picasso.get().load(sharedPreferences.getString(imageName, "")).resize(0, 100).into(imageView)
            Glide.with(this).load(sharedPreferences.getString(imageName, "")).centerInside()
                .into(imageView)
        } catch (e: Exception) {
            // Handling the null pointer exception
        }
    }

    private fun updateAnxietyProgressBar(sharedPref: SharedPreferences) {
        val staiScore = sharedPref.getInt("StaiScore", -1)

        if (staiScore != -1) {
            binding.anxietyProgress.isIndeterminate = false
            binding.anxietyProgress.max = 80
            activity?.runOnUiThread {
                ObjectAnimator.ofInt(binding.anxietyProgress, "progress", staiScore)
                    .setDuration(500).start()
            }

            binding.anxietyProgress.tooltipText = "$staiScore/80"
            binding.anxietyLabel.tooltipText = "$staiScore/80"

            when (staiScore) {
                in 20..40 -> {
                    binding.anxietyProgress.setIndicatorColor(Color.parseColor("#69B34C"))
                }
                in 41..60 -> {
                    binding.anxietyProgress.setIndicatorColor(Color.parseColor("#FF8E15"))
                }
                else -> {
                    binding.anxietyProgress.setIndicatorColor(Color.parseColor("#FF0D0D"))
                }
            }
        }
    }

    private fun updateDepressionProgressBar(sharedPref: SharedPreferences) {
        val score = sharedPref.getInt("Phq2Score", -1)

        if (score != -1) {
            val progressBar = binding.depressionProgressBar
            val label = binding.depressionLabel


            progressBar.max = 6
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
            }
            progressBar.tooltipText = "$score/${progressBar.max}"
            label.tooltipText = "$score/${progressBar.max}"

            when (score) {
                in 0..2 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#69B34C"))
                }
                in 3..6 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#FF0D0D"))
                }
            }
        }
    }

    private fun updateSelfEfficacyProgressBar(sharedPref: SharedPreferences) {
        val score = sharedPref.getInt("PseqScore", -1)

        if (score != -1) {
            val progressBar = binding.selfEfficacyProgressBar
            val label = binding.selfEfficacyLabel


            progressBar.max = 60
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
            }
            progressBar.tooltipText = "$score/${progressBar.max}"
            label.tooltipText = "$score/${progressBar.max}"

            when (score) {
                in 0..19 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#69B34C"))
                }
                in 20..60 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#FF0D0D"))
                }
            }
        }
    }

    private fun updateThoughtsAboutPainProgressBar(sharedPref: SharedPreferences) {
        val score = sharedPref.getInt("ThoughtsAboutPainScore", -1)

        if (score != -1) {
            val progressBar = binding.thoughtsAboutPainProgressBar
            val label = binding.thoughtsAboutPainLabel


            progressBar.max = 52
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
            }
            progressBar.tooltipText = "$score/${progressBar.max}"
            label.tooltipText = "$score/${progressBar.max}"

            when (score) {
                in 0..29 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#69B34C"))
                }
                in 30..52 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#FF0D0D"))
                }
            }
        }
    }

    private fun updateLonelinessScoreProgressBar(sharedPref: SharedPreferences) {
        val score = sharedPref.getInt("LonelinessScaleScore", -1)

        if (score != -1) {
            val progressBar = binding.LonelinessProgressBar
            val label = binding.LonelinessLabel


            progressBar.max = 9
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
            }
            progressBar.tooltipText = "$score/${progressBar.max}"
            label.tooltipText = "$score/${progressBar.max}"

            when (score) {
                in 3..5 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
                }
                in 6..9 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
                }
            }
        }
    }

    private fun updatePatientCaregiverRelationshipProgressBar() {
        var sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.patientData), Context.MODE_PRIVATE
        )
        val patientScore = sharedPref.getInt("SRIScore", -1)

        sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.caregiverData), Context.MODE_PRIVATE)

        val caregiverScore = sharedPref.getInt("SRIScore", -1)

        val label = binding.sriLabel
        val patientProgressBar = binding.sriPatientProgressBar
        val caregiverProgressBar = binding.sriCaregiverProgressBar

        if (patientScore != -1) {
            patientProgressBar.max = 18
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(patientProgressBar, "progress", patientScore).setDuration(500).start()
            }
            when (patientScore) {
                in 14..18 -> {
                    patientProgressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
                }
                in 3..13 -> {
                    patientProgressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
                }
            }
        }


        if (caregiverScore != -1) {
            caregiverProgressBar.max = 18
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(caregiverProgressBar, "progress", caregiverScore).setDuration(500).start()
            }
            when (caregiverScore) {
                in 14..18 -> {
                    caregiverProgressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
                }
                in 3..13 -> {
                    caregiverProgressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
                }
            }
        }

        if(patientScore != -1 && caregiverScore!= -1) {
            label.tooltipText = "Patient: $patientScore/${patientProgressBar.max}\nCaregiver: $caregiverScore/${patientProgressBar.max}"
        }
        else if(patientScore != -1) {
            label.tooltipText = "Patient: $patientScore/${patientProgressBar.max}"
        }
        else if(caregiverScore != -1) {
            label.tooltipText = "Caregiver: $caregiverScore/${patientProgressBar.max}"
        }
    }

    private fun updateIadlsProgressBar() {
        var sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.patientData), Context.MODE_PRIVATE
        )
        val patientScore = sharedPref.getInt("IADLSScore", -1)
        val patientGender = sharedPref.getString("gender", "")

        sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.caregiverData), Context.MODE_PRIVATE)

        val caregiverScore = sharedPref.getInt("IADLSScore", -1)
        val caregiverGender = sharedPref.getString("gender", "")

        val label = binding.iadlsLabel
        val patientProgressBar = binding.iadlsPatientProgressBar
        val caregiverProgressBar = binding.iadlsCaregiverProgressBar

        if (patientScore != -1) {

            if (patientGender == "Male") {
                patientProgressBar.max = 5
            }
            else {
                patientProgressBar.max = 8
            }

            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(patientProgressBar, "progress", patientScore).setDuration(500).start()
            }
        }

        if (caregiverScore != -1) {

            if (caregiverGender == "Male") {
                caregiverProgressBar.max = 5
            }
            else {
                caregiverProgressBar.max = 8
            }

            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(caregiverProgressBar, "progress", caregiverScore).setDuration(500).start()
            }
        }




        if(patientScore != -1 && caregiverScore!= -1) {
            label.tooltipText = "Patient: $patientScore/${patientProgressBar.max}\nCaregiver: $caregiverScore/${caregiverProgressBar.max}"
        }
        else if(patientScore != -1) {
            label.tooltipText = "Patient: $patientScore/${patientProgressBar.max}"
        }
        else if(caregiverScore != -1)  {
            label.tooltipText = "Caregiver: $caregiverScore/${caregiverProgressBar.max}"
        }
    }

    private fun updateSocialIsolationProgressBar(sharedPref: SharedPreferences) {
        val score = sharedPref.getInt("LSNS6Score", -1)

        if (score != -1) {
            val progressBar = binding.SocialIsolationProgressBar
            val label = binding.SocialIsolationLabel


            progressBar.max = 30
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
            }
            progressBar.tooltipText = "$score/${progressBar.max}"
            label.tooltipText = "$score/${progressBar.max}"

            when (score) {
                in 12..30 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
                }
                in 0..11 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
                }
            }
        }
    }

    private fun updateAdlsProgressBar() {
        var sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.patientData), Context.MODE_PRIVATE
        )
        val patientScore = sharedPref.getInt("AdlsScore", -1)
        val patientProgressBar = binding.patientAdlsProgressBar
        val label = binding.patientAdlsLabel

        sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.caregiverData), Context.MODE_PRIVATE
        )

        val caregiverScore = sharedPref.getInt("AdlsScore", -1)
        val caregiverProgressBar = binding.caregiverAdlsProgressBar

        if (patientScore != -1) {
            patientProgressBar.max = 6
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(patientProgressBar, "progress", patientScore).setDuration(500).start()
            }
            patientProgressBar.tooltipText = "$patientScore/${patientProgressBar.max}"


            when (patientScore) {
                in 6 downTo 5 -> {
                    patientProgressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
                }
                in 4 downTo 3 -> {
                    patientProgressBar.setIndicatorColor(Color.parseColor("#FF8E15")) // Orange
                }
                in 2 downTo 0 -> {
                    patientProgressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
                }
            }
        }

        if (caregiverScore != -1) {
            caregiverProgressBar.max = 6
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(caregiverProgressBar, "progress", caregiverScore).setDuration(500).start()
            }
            caregiverProgressBar.tooltipText = "$caregiverProgressBar/${caregiverProgressBar.max}"

            when (caregiverScore) {
                in 6 downTo 5 -> {
                    caregiverProgressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
                }
                in 4 downTo 3 -> {
                    caregiverProgressBar.setIndicatorColor(Color.parseColor("#FF8E15")) // Orange
                }
                in 2 downTo 0 -> {
                    caregiverProgressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
                }
            }
        }

        if(patientScore != -1 && caregiverScore!= -1) {
            label.tooltipText = "Patient: $patientScore/${patientProgressBar.max}\nCaregiver: $caregiverScore/${caregiverProgressBar.max}"
        }
        else if(patientScore != -1) {
            label.tooltipText = "Patient: $patientScore/${patientProgressBar.max}"
        }
        else if(caregiverScore != -1)  {
            label.tooltipText = "Caregiver: $caregiverScore/${caregiverProgressBar.max}"
        }
    }

    private fun updateHealthLiteracyProgressBar(sharedPref: SharedPreferences) {
        val score = sharedPref.getInt("BriefScore", -1)

        if (score != -1) {
            val progressBar = binding.HealthLiteracyProgressBar
            val label = binding.HealthLiteracyLabel


            progressBar.max = 20
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
            }
            progressBar.tooltipText = "$score/${progressBar.max}"
            label.tooltipText = "$score/${progressBar.max}"

            when (score) {
                in 17 .. 20 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
                }
                in 13..16 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#FF8E15")) // Orange
                }
                in 4..12 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
                }
            }
        }
    }

    private fun updateDifficultyGettingMedicationProgressBar(sharedPref: SharedPreferences) {
        val score = sharedPref.getInt("ContextScore1", -1)

        if (score != -1) {
            val progressBar = binding.DifficultyGettingMedicationProgressBar
            val label = binding.DifficultyGettingMedicationLabel


            progressBar.max = 3
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
            }
            progressBar.tooltipText = "$score/${progressBar.max}"
            label.tooltipText = "$score/${progressBar.max}"

            when (score) {
                1 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
                }
                2 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#FF8E15")) // Orange
                }
                3 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
                }
            }
        }
    }

    private fun updateDifficultyGettingToClinicProgressBar(sharedPref: SharedPreferences) {
        val score = sharedPref.getInt("ContextScore2", -1)

        if (score != -1) {
            val progressBar = binding.DifficultyGettingToClinicProgressBar
            val label = binding.DifficultyGettingToClinicLabel


            progressBar.max = 1
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(progressBar, "progress", 1).setDuration(500).start()
            }
            progressBar.tooltipText = "$score/${progressBar.max}"
            label.tooltipText = "$score/${progressBar.max}"

            when (score) {
                0 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
                }
                1 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
                }
            }


        }
    }

    private fun updateDifficultyAffordingCareProgressBar(sharedPref: SharedPreferences) {
        val score = sharedPref.getInt("ContextScore3", -1)

        if (score != -1) {
            val progressBar = binding.DifficultyAffordingCareProgressBar
            val label = binding.DifficultyAffordingCareLabel


            progressBar.max = 3
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
            }
            progressBar.tooltipText = "$score/${progressBar.max}"
            label.tooltipText = "$score/${progressBar.max}"

            when (score) {
                1 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#69B34C")) // Green
                }
                2 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#FF8E15")) // Orange
                }
                3 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#FF0D0D")) // Red
                }
            }

        }
    }

    private fun updateCaregiverBurdenScaleProgressBar() {
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.caregiverData), Context.MODE_PRIVATE
        )
        val score = sharedPref.getInt("CBSScore", -1)

        if (score != -1) {
            val progressBar = binding.caregiverWorkloadProgressBar
            val label = binding.caregiverWorkloadLabel


            progressBar.max = 88
            requireActivity().runOnUiThread {
                ObjectAnimator.ofInt(progressBar, "progress", score).setDuration(500).start()
            }
            progressBar.tooltipText = "$score/${progressBar.max}"
            label.tooltipText = "$score/${progressBar.max}"

            when (score) {
                in 0..17 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#69B34C"))
                }
                in 18..88 -> {
                    progressBar.setIndicatorColor(Color.parseColor("#FF0D0D"))
                }
            }
        }
    }

    private fun updateOpenEndedQuestions(sharedPref: SharedPreferences) {
        val q1 = sharedPref.getString("openEndedQuestionnaireQ1", "No response yet")
        val q2 = sharedPref.getString("openEndedQuestionnaireQ2", "No response yet")
        val q3 = sharedPref.getString("openEndedQuestionnaireQ3", "No response yet")
        val q4 = sharedPref.getString("openEndedQuestionnaireQ4", "No response yet")
        val q5 = sharedPref.getString("openEndedQuestionnaireQ5", "No response yet")

        binding.q1.hint = q1
        binding.q2.hint = q2
        binding.q3.hint = q3
        binding.q4.hint = q4
        binding.q5.hint = q5

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

            }
        }
    }

}
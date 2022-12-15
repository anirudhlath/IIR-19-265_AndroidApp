package com.example.joriebutlerprojectnative

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isNotEmpty
import com.bumptech.glide.Glide
import com.example.joriebutlerprojectnative.databinding.FragmentCareproviderReviewBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso


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

        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.patientData), Context.MODE_PRIVATE
        )

        updatePatientData(sharedPref)
        updateAnxietyProgressBar(sharedPref)


        return rootView
    }

    private fun updatePatientData(sharedPref: SharedPreferences) {
        loadImage("profilePhotoURI",binding.imageView, sharedPref)
        binding.patientFullNameLabel.text =
            sharedPref.getString("fName", "NIL") + " " + sharedPref.getString("lName", "NIL")
        binding.patientDobLabel.text = sharedPref.getString("dob", "NIL")
        binding.patientMrnLabel.text = sharedPref.getString("mrn", "NIL")
        binding.patientTravelLabel.text = sharedPref.getString("travel", "NIL")


    }

    private fun loadImage(imageName: String, imageView: ImageView, sharedPreferences: SharedPreferences) {
        try {
//            Picasso.get().load(sharedPreferences.getString(imageName, "")).resize(0, 100).into(imageView)
            Glide.with(this).load(sharedPreferences.getString(imageName, "")).centerInside().into(imageView)
        }
        catch (e: Exception) {
            // Handling the null pointer exception
        }
    }

    private fun updateAnxietyProgressBar(sharedPref: SharedPreferences) {
        val staiScore = sharedPref.getInt("StaiScore", -1)

        if (staiScore != -1) {
            binding.anxietyProgress.isIndeterminate = false
            binding.anxietyProgress.max = 80
            binding.anxietyProgress.progress = staiScore
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

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

            }
        }
    }

}
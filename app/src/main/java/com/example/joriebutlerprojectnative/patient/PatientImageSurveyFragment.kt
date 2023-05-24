/*
 *     Created by Anirudh Lath on 4/4/23, 8:40 PM
 *     anirudhlath@gmail.com
 *     Last modified 4/4/23, 8:40 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.patient

import android.R.attr.bitmap
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.impl.utils.ContextUtil.getApplicationContext
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.joriebutlerprojectnative.BuildConfig
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.FragmentPatientImageSurveyBinding
import java.io.File
import java.io.IOException
import java.text.DateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [PatientImageSurveyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientImageSurveyFragment : Fragment(), OnClickListener {

    // Private global variables
    private var _binding: FragmentPatientImageSurveyBinding? = null
    private val binding get() = _binding!!
    private var uri: Uri? = null
    private var photo1Launcher: ActivityResultLauncher<Uri>? = null
    private var photo2Launcher: ActivityResultLauncher<Uri>? = null
    private var photo3Launcher: ActivityResultLauncher<Uri>? = null
    private var photo4Launcher: ActivityResultLauncher<Uri>? = null
    private var photo5Launcher: ActivityResultLauncher<Uri>? = null
    private var photo6Launcher: ActivityResultLauncher<Uri>? = null
    private var photo7Launcher: ActivityResultLauncher<Uri>? = null
    private var photo8Launcher: ActivityResultLauncher<Uri>? = null
    private var photo9Launcher: ActivityResultLauncher<Uri>? = null
    private var photo10Launcher: ActivityResultLauncher<Uri>? = null

    private var currentImagePath: String = ""

    // Create image file
    fun createImageFile(filename: String): File {

        val timeStamp: String = DateFormat.getDateInstance().format(Date())
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            filename + timeStamp,
            ".png",
            storageDir
        ).apply {
            currentImagePath = absolutePath
        }
    }


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
        _binding = FragmentPatientImageSurveyBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root



        // Initialize all the camera launchers
        photo1Launcher = setupPhotoActivityResultLauncher("photo1", binding.q1Preview)
        photo2Launcher = setupPhotoActivityResultLauncher("photo2", binding.q2Preview)
        photo3Launcher = setupPhotoActivityResultLauncher("photo3", binding.q3Preview)
        photo4Launcher = setupPhotoActivityResultLauncher("photo4", binding.q4Preview)
        photo5Launcher = setupPhotoActivityResultLauncher("photo5", binding.q5Preview)
        photo6Launcher = setupPhotoActivityResultLauncher("photo6", binding.q6Preview)
        photo7Launcher = setupPhotoActivityResultLauncher("photo7", binding.q7Preview)
        photo8Launcher = setupPhotoActivityResultLauncher("photo8", binding.q8Preview)
        photo9Launcher = setupPhotoActivityResultLauncher("photo9", binding.q9Preview)
        photo10Launcher = setupPhotoActivityResultLauncher("photo10", binding.q10Preview)



        return rootView
    }

    override fun onStart() {
        super.onStart()
        binding.fab1.setOnClickListener(this)
        binding.fab2.setOnClickListener(this)
        binding.fab3.setOnClickListener(this)
        binding.fab4.setOnClickListener(this)
        binding.fab5.setOnClickListener(this)
        binding.fab6.setOnClickListener(this)
        binding.fab7.setOnClickListener(this)
        binding.fab8.setOnClickListener(this)
        binding.fab9.setOnClickListener(this)
        binding.fab10.setOnClickListener(this)

        // Load the pictures that have already been taken.
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.patientData), Context.MODE_PRIVATE
        )

        loadImage("photo1", binding.q1Preview, sharedPref)
        loadImage("photo2", binding.q2Preview, sharedPref)
        loadImage("photo3", binding.q3Preview, sharedPref)
        loadImage("photo4", binding.q4Preview, sharedPref)
        loadImage("photo5", binding.q5Preview, sharedPref)
        loadImage("photo6", binding.q6Preview, sharedPref)
        loadImage("photo7", binding.q7Preview, sharedPref)
        loadImage("photo8", binding.q8Preview, sharedPref)
        loadImage("photo9", binding.q9Preview, sharedPref)
        loadImage("photo10", binding.q10Preview, sharedPref)
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
                R.id.fab_1 -> {
                    takePicture("photo1", photo1Launcher)
                    return
                }
                R.id.fab_2 -> {
                    takePicture("photo2", photo2Launcher)
                    return
                }
                R.id.fab_3 -> {
                    takePicture("photo3", photo3Launcher)
                    return
                }
                R.id.fab_4 -> {
                    takePicture("photo4", photo4Launcher)
                    return
                }
                R.id.fab_5 -> {
                    takePicture("photo5", photo5Launcher)
                    return
                }
                R.id.fab_6 -> {
                    takePicture("photo6", photo6Launcher)
                    return
                }
                R.id.fab_7 -> {
                    takePicture("photo7", photo7Launcher)
                    return
                }
                R.id.fab_8 -> {
                    takePicture("photo8", photo8Launcher)
                    return
                }
                R.id.fab_9 -> {
                    takePicture("photo9", photo9Launcher)
                    return
                }
                R.id.fab_10 -> {
                    takePicture("photo10", photo10Launcher)
                    return
                }
            }
        }
    }

    private fun setupPhotoActivityResultLauncher(imageName: String, imageView: ImageView): ActivityResultLauncher<Uri> {


        return (registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                Log.d("Capture Image", "Got image at: $uri")


//                Picasso.get().load(uri).resize(0,500).into(imageView)
                Glide.with(this).load(uri).centerInside().override(650).into(imageView)


                val sharedPref = requireActivity().getSharedPreferences(
                    getString(R.string.patientData), Context.MODE_PRIVATE
                )
                val editor = sharedPref.edit()
                editor.putString(imageName, uri.toString())
                editor.apply()

            }
        })

    }

    private fun takePicture(imageName: String, activityResultLauncher: ActivityResultLauncher<Uri>?) {
        uri = FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.fileprovider", createImageFile(imageName))
        activityResultLauncher!!.launch(uri)
    }

    private fun loadImage(imageName: String, imageView: ImageView, sharedPreferences: SharedPreferences) {
        try {
            Glide.with(this).load(sharedPreferences.getString(imageName, "")).centerInside().override(650).into(imageView)

        }
        catch (e: Exception) {
            // Handling the null pointer exception
        }
    }

}



/*
 *     Created by Anirudh Lath on 4/4/23, 8:40 PM
 *     anirudhlath@gmail.com
 *     Last modified 4/4/23, 8:40 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.caregiver

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.databinding.FragmentCaregiverSignUpBinding
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 * Use the [CaregiverSignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CaregiverSignUpFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCaregiverSignUpBinding? = null
    private val binding get() = _binding!!
//    private var photo1Launcher: ActivityResultLauncher<Uri>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCaregiverSignUpBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root

        val button = rootView.findViewById<Button>(R.id.buttonCaregiverFinishProfile)
        button.setOnClickListener(this)
//        photo1Launcher = setupPhotoActivityResultLauncher("profilePhoto", binding.imageButton)
        return rootView
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.buttonCaregiverFinishProfile -> {

                    if (!binding.textFieldFullName.editText?.text.isNullOrEmpty()
                        && !binding.textFieldRelationship.editText?.text.isNullOrEmpty()
                        && !binding.menuLocation.editText?.text.isNullOrEmpty()
                        && !binding.menuFrequencyOfContact.editText?.text.isNullOrEmpty()
                    ) {
                        val sharedPref = requireActivity().getSharedPreferences(
                            getString(R.string.caregiverData), Context.MODE_PRIVATE
                        )
                        val editor = sharedPref.edit()
                        editor.putString("fullName", binding.textFieldFullName.editText?.text.toString().trim())
                        editor.putString("relationship", binding.textFieldRelationship.editText?.text.toString().trim())
                        editor.putString("location", binding.menuLocation.editText?.text.toString())
                        editor.putString("frequency",binding.menuFrequencyOfContact.editText?.text.toString())
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
                        Log.d("UI", "Profile finish button clicked.")
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView2, CaregiverHomePageFragment()).commit()
                }

            }
        }
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.fade_out)
        }
    }


}
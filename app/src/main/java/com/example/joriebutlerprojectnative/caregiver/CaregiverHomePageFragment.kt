/*
 * *
 *  * Created by Anirudh Lath on 12/16/22, 11:37 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 12/16/22, 11:37 AM
 *
 */

package com.example.joriebutlerprojectnative.caregiver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment

/**
 * A simple [Fragment] subclass.
 * Use the [CaregiverHomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CaregiverHomePageFragment : Fragment(), OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_caregiver_home_page, container, false)
        rootView.findViewById<ImageButton>(R.id.surveysButton).setOnClickListener(this)

        return rootView
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        if(v != null) {
            when (v.id) {
                R.id.surveysButton -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView2, CaregiverSurveysFragment()).commit()
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
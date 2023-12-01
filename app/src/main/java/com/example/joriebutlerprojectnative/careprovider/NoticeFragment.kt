/*
 *     Created by Anirudh Lath in 2021
 *     anirudhlath@gmail.com
 *     Last modified 11/30/23, 6:38 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.careprovider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.careprovider.CareproviderReviewFragment.*
import com.example.joriebutlerprojectnative.databinding.FragmentNoticeBinding

/**
 * A simple [Fragment] subclass. Use the [NoticeFragment.newInstance] factory method to create an
 * instance of this fragment.
 */
class NoticeFragment : Fragment(), OnClickListener {

  private var _binding: FragmentNoticeBinding? = null
  private val binding
    get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentNoticeBinding.inflate(inflater, container, false)
    val rootView = _binding!!.root
    binding.floatingActionButton.setOnClickListener(this)

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
        R.id.floating_action_button -> {
          //
          // requireActivity().findViewById<LinearProgressIndicator>(R.id.caregiverLoadingBar).visibility =
          //                        View.VISIBLE
          val fragment = CareproviderReviewFragment()
          parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            .replace(R.id.fragmentContainerView3, fragment)
            .commit()

          return
        }
      }
    }
  }
}

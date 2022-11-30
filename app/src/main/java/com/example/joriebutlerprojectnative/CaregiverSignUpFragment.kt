package com.example.joriebutlerprojectnative

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


/**
 * A simple [Fragment] subclass.
 * Use the [CaregiverSignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CaregiverSignUpFragment : Fragment(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_caregiver_sign_up, container, false)
        val button = rootView.findViewById<Button>(R.id.buttonCaregiverFinishProfile)
        button.setOnClickListener(this)
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
                    Log.d("UI", "Profile finish button clicked.")
                    parentFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2, CaregiverHomePageFragment()).commit()
                }

            }
        }
    }


}
package com.example.joriebutlerprojectnative

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button

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

        return rootView
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}
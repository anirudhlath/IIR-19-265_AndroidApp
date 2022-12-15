package com.example.joriebutlerprojectnative.surveys

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.joriebutlerprojectnative.R

// TODO: Compelete the layout and then implement this survey after Jories responds.


/**
 * A simple [Fragment] subclass.
 * Use the [InstrumentalActivitiesOfDailyLifeScaleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InstrumentalActivitiesOfDailyLifeScaleFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.questionnaire_instrumental_activities_of_daily_life_scale,
            container,
            false
        )
    }

}
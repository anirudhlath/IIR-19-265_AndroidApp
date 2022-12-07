package com.example.joriebutlerprojectnative

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


/**
 * A simple [Fragment] subclass.
 * Use the [PatientSignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientSignUpFragment : Fragment(), OnClickListener {

    private var fNameEditText: TextInputLayout? = null
    private var lNameEditText: TextInputLayout? = null
    private var dobEditText: TextInputEditText? = null
    private var mrnEditText: TextInputLayout? = null
    private var genderMenu: TextInputLayout? = null
    private var travelMenu: TextInputLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_patient_sign_up, container, false)
        val button = rootView?.findViewById<Button>(R.id.buttonPatientFinishProfile)
        fNameEditText = rootView?.findViewById(R.id.textFieldFirstName)
        lNameEditText = rootView?.findViewById(R.id.textFieldLastName)
        dobEditText = rootView?.findViewById(R.id.textFieldDob)
        mrnEditText = rootView?.findViewById(R.id.textFieldMrn)
        genderMenu = rootView?.findViewById(R.id.menuGender)
        travelMenu = rootView?.findViewById(R.id.menuTravel)

//        dobEditText?.setOnClickListener {
//            Log.d("OnClick", "Date Picker init")
//            val datePicker =
//                MaterialDatePicker.Builder.datePicker()
//                    .setTitleText("Select your birth date")
//                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
//                    .build()
//
//            datePicker.show(parentFragmentManager, "DOB_picker")
//            datePicker.addOnPositiveButtonClickListener {
//                dobEditText?.setText(datePicker.headerText)
//            }
//            return@setOnClickListener
//        }


        fNameEditText?.setOnClickListener(this)
        lNameEditText?.setOnClickListener(this)
        dobEditText?.setOnClickListener(this)
        mrnEditText?.setOnClickListener(this)
        genderMenu?.setOnClickListener(this)
        travelMenu?.setOnClickListener(this)
        button?.setOnClickListener(this)

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
                R.id.buttonPatientFinishProfile -> {
                    Log.d("UI", "Profile finish button clicked.")
                    val sharedPref = requireActivity().getSharedPreferences(
                        getString(R.string.patientData), Context.MODE_PRIVATE
                    )

                    // Save data
                    if (!fNameEditText?.editText?.text.isNullOrEmpty() &&
                        !lNameEditText?.editText?.text.isNullOrEmpty() &&
                        !dobEditText?.text.isNullOrEmpty() &&
                        !mrnEditText?.editText?.text.isNullOrEmpty() &&
                        mrnEditText?.editText?.text.toString().trim().length == 4 &&
                        !genderMenu?.editText?.text.isNullOrEmpty() &&
                        !travelMenu?.editText?.text.isNullOrEmpty()
                    ) {
                        Log.d("SharedPreferences", "Trying to save data save data...")
                        Log.d(
                            "SharedPreferences",
                            "First Name      : " + fNameEditText?.editText?.text.toString()
                        )
                        Log.d(
                            "SharedPreferences",
                            "Last Name       : " + lNameEditText?.editText?.text.toString()
                        )
                        Log.d(
                            "SharedPreferences",
                            "Date of Birth   : " + dobEditText?.text.toString()
                        )
                        Log.d(
                            "SharedPreferences",
                            "MRN             : " + mrnEditText?.editText?.text.toString()
                        )
                        Log.d(
                            "SharedPreferences",
                            "Gender          : " + genderMenu?.editText?.text.toString()
                        )
                        Log.d(
                            "SharedPreferences",
                            "Travel          : " + travelMenu?.editText?.text.toString()
                        )


                        val editor = sharedPref.edit()
                        editor.putString("fName", fNameEditText?.editText?.text.toString())
                        editor.putString("lName", lNameEditText?.editText?.text.toString())
                        editor.putString("dob", dobEditText?.text.toString())
                        editor.putString("mrn", mrnEditText?.editText?.text.toString())
                        editor.putString("gender", genderMenu?.editText?.text.toString())
                        editor.putString("travel", travelMenu?.editText?.text.toString())
                        editor.apply()
                    } else {
                        val contextView = requireView()
                        Snackbar.make(
                            contextView,
                            "Please complete all the fields.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        return
                    }

                    // Debug Logs and confirm if the data was saved
                    Log.d("SharedPreferences", "Loading the save data...")
                    Log.d(
                        "SharedPreferences",
                        "First Name      : " + sharedPref.getString("fName", "NIL").toString()
                    )
                    Log.d(
                        "SharedPreferences",
                        "Last Name       : " + sharedPref.getString("lName", "NIL").toString()
                    )
                    Log.d(
                        "SharedPreferences",
                        "Date of Birth   : " + sharedPref.getString("dob", "NIL").toString()
                    )
                    Log.d(
                        "SharedPreferences",
                        "MRN             : " + sharedPref.getString("mrn", "NIL").toString()
                    )
                    Log.d(
                        "SharedPreferences",
                        "Gender          : " + sharedPref.getString("gender", "NIL").toString()
                    )
                    Log.d(
                        "SharedPreferences",
                        "Travel          : " + sharedPref.getString("travel", "NIL").toString()
                    )

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, PatientHomePageFragment()).commit()
                }

                R.id.textFieldFirstName -> {

                }
                R.id.textFieldLastName -> {

                }
                R.id.textFieldDob -> {
                    Log.d("OnClick", "Date Picker init")
                    val datePicker =
                        MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Select your birth date")
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .build()

                    datePicker.show(parentFragmentManager, "DOB_picker")
                    datePicker.addOnPositiveButtonClickListener {
                        dobEditText?.setText(datePicker.headerText)
                    }


                }
                R.id.textFieldMrn -> {

                }
                R.id.menuGender -> {

                }
                R.id.menuTravel -> {

                }

            }
        }
    }


}
package com.example.joriebutlerprojectnative

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.example.joriebutlerprojectnative.databinding.FragmentCareproviderReviewBinding
import com.example.joriebutlerprojectnative.databinding.FragmentNoticeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [NoticeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoticeFragment : Fragment(), OnClickListener{

    private var _binding: FragmentNoticeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentNoticeBinding.inflate(inflater, container, false)
        val rootView = _binding!!.root
        binding.floatingActionButton.setOnClickListener(this)

        return rootView
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.floating_action_button -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3, CareproviderReviewFragment()).commit()
                    return
                }
            }
        }
    }


}
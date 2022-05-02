package com.androidcourse.g3.beamax.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.databinding.FragmentOnboarding3Binding



class onboarding3 : Fragment() {

    private lateinit var binding: FragmentOnboarding3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentOnboarding3Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.onboarding3NextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_viewPaperFragment2_to_welcome)
        }
    }


}
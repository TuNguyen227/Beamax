package com.androidcourse.g3.beamax.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidcourse.g3.beamax.databinding.FragmentOnboarding2Binding



class onboarding2 : Fragment() {

    private lateinit var binding: FragmentOnboarding2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentOnboarding2Binding.inflate(inflater,container,false)
        return binding.root
    }


}
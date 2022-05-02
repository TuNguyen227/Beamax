package com.androidcourse.g3.beamax.screens

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.androidcourse.g3.beamax.R

import com.androidcourse.g3.beamax.databinding.FragmentOnboarding1Binding
import java.util.prefs.Preferences


class onboarding1 : Fragment() {


    private lateinit var binding: FragmentOnboarding1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate<FragmentOnboarding1Binding?>(inflater,
            R.layout.fragment_onboarding1,container,false)
        return binding.root
    }




}
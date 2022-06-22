package com.androidcourse.g3.beamax.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.base.BaseFragment
import com.androidcourse.g3.beamax.databinding.FragmentOnboarding3Binding


class onboarding3 : BaseFragment() {

    private lateinit var binding: FragmentOnboarding3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun init() {}

    override fun setUpUI() {}

    override fun setListener() {
        binding.onboarding3NextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_viewPaperFragment2_to_welcome)
        }
    }

    override fun setObserver() {}

    override fun setAnimation() {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentOnboarding3Binding.inflate(inflater,container,false)
        return binding.root
    }
    companion object{
        private var instance: onboarding3?=null
        fun getInstance(): onboarding3
        {
            if (instance==null)
            {
                instance= onboarding3()
            }
            return instance as onboarding3
        }
    }





}
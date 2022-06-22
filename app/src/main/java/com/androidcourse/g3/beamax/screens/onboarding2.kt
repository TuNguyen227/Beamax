package com.androidcourse.g3.beamax.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidcourse.g3.beamax.base.BaseFragment
import com.androidcourse.g3.beamax.databinding.FragmentOnboarding2Binding


class onboarding2 : BaseFragment(){

    private lateinit var binding: FragmentOnboarding2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun init() {}

    override fun setUpUI() {}

    override fun setListener() {}

    override fun setObserver() {}

    override fun setAnimation() {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentOnboarding2Binding.inflate(inflater,container,false)
        return binding.root
    }

    companion object{
        private var instance: onboarding2?=null
        fun getInstance(): onboarding2
        {
            if (instance==null)
            {
                instance= onboarding2()
            }
            return instance as onboarding2
        }
    }




}
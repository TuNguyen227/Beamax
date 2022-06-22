package com.androidcourse.g3.beamax.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.base.BaseFragment

import com.androidcourse.g3.beamax.databinding.FragmentOnboarding1Binding


class onboarding1 : BaseFragment() {


    private lateinit var binding: FragmentOnboarding1Binding
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
        binding=DataBindingUtil.inflate<FragmentOnboarding1Binding?>(inflater,
            R.layout.fragment_onboarding1,container,false)
        return binding.root
    }

    companion object{
        private var instance: onboarding1?=null
        fun getInstance(): onboarding1
        {
            if (instance==null)
            {
                instance= onboarding1()
            }
            return instance as onboarding1
        }
    }


}
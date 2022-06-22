package com.androidcourse.g3.beamax.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.base.BaseFragment
import com.androidcourse.g3.beamax.databinding.FragmentNewPasswordBinding



class NewPassword : BaseFragment() {

    private lateinit var binding: FragmentNewPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun init() {

    }

    override fun setUpUI() {

    }

    override fun setListener() {
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_newPassword_to_resetPassword)
        }
    }

    override fun setObserver() {

    }

    override fun setAnimation() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentNewPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }


}
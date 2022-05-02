package com.androidcourse.g3.beamax.screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.navigation.fragment.findNavController
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.databinding.CustomeToastBinding
import com.androidcourse.g3.beamax.databinding.FragmentSignInBinding

import com.androidcourse.group3.beamax.ViewModel.SignInViewModel


import com.google.firebase.auth.FirebaseAuth



class SignIn : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var toastbinding: CustomeToastBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var toastview:View
    private lateinit var signInViewModel: SignInViewModel
    private val RC_SIGN_IN = 120
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        signInViewModel=ViewModelProvider(this).get(SignInViewModel::class.java)
        toastbinding= DataBindingUtil.inflate(inflater, R.layout.custome_toast,container,false)
        firebaseAuth= FirebaseAuth.getInstance()
        toastview = layoutInflater.inflate(R.layout.custome_toast, toastbinding.llcontainer,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.SignUpText.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_signUp)
        }
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_welcome)
        }
        binding.forgetPasswordText.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_resetPassword)
        }

        binding.signinBtn.setOnClickListener {
            val email : String= binding.editTextMail.text.toString().trim()
            val password :String= binding.editTextPassword.text.toString().trim()

            if (signInViewModel.CheckingEmailPasswordForSignIn(email,password,password))
            {
                signInViewModel.SignIn(email,password)
            }


        }
        SignIn_succes_listener()
        SignIn_fail_listener()
    }

    fun SignIn_succes_listener()
    {
        signInViewModel.IsSignIn.observe(viewLifecycleOwner){
            if (it)
            {
                findNavController().navigate(R.id.action_signIn_to_home2)
            }

        }
    }
    fun SignIn_fail_listener()
    {
        signInViewModel.error.observe(viewLifecycleOwner)
        {
            val toast=Toast(context)

            toast.duration=Toast.LENGTH_SHORT
            toastview.findViewById<ImageView>(R.id.toast_ic).setBackgroundResource(R.drawable.ic_baseline_mail_24)
            toastview.findViewById<TextView>(R.id.toast_content).setText(it)
            toast.view=toastview


            toast.show()
        }
    }
}





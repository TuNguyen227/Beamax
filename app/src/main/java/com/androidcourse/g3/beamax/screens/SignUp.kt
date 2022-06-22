package com.androidcourse.g3.beamax.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.base.BaseFragment
import com.androidcourse.g3.beamax.databinding.CustomeToastBinding
import com.androidcourse.g3.beamax.databinding.FragmentSignUpBinding

import com.androidcourse.group3.beamax.ViewModel.SignUpViewModel

import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel


class SignUp : BaseFragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private  val signUpViewModel: SignUpViewModel by stateViewModel()
    private lateinit var ToastView:View
    private lateinit var toastBinding: CustomeToastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun init() {

    }

    override fun setUpUI() {

    }

    override fun setListener() {
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signUp_to_signIn)
        }
        binding.signUpBtn.setOnClickListener {
            val email=binding.editTextMail.text.toString()
            val password=binding.editTextPassword.text.toString().trim()
            val repeatpassword=binding.editTextRepeatPassword.text.toString().trim()

            if(signUpViewModel.CheckingEmailPasswordForSignUp(email,password,repeatpassword))
            {
                signUpViewModel.SignUP(email,password,repeatpassword)
            }


        }
    }

    override fun setObserver() {
        onSignUpSuccessObserver()
        onSignUpFailObserver()
    }

    override fun setAnimation() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSignUpBinding.inflate(inflater,container,false)
        toastBinding= CustomeToastBinding.inflate(inflater,container,false)
        firebaseAuth= FirebaseAuth.getInstance()
        ToastView=layoutInflater.inflate(R.layout.custome_toast,toastBinding.llcontainer,false)
        return binding.root
    }

    fun onSignUpSuccessObserver()
    {


        signUpViewModel.sendEmailVerification.observe(viewLifecycleOwner)
        {
            if (it)
            {
                val toast=Toast(context)
                toast.duration=Toast.LENGTH_SHORT

                ToastView.findViewById<ImageView>(R.id.toast_ic).setBackgroundResource(R.drawable.ic_baseline_mail_24)
                ToastView.findViewById<TextView>(R.id.toast_content).setText("Please go to your email and verify")
                toast.view=ToastView
                toast.show()
            }


        }
    }
    fun onSignUpFailObserver()
    {
        signUpViewModel.errorLiveData.observe(viewLifecycleOwner)
        {
            val toast=Toast(context)
            toast.duration=Toast.LENGTH_SHORT
            ToastView.findViewById<ImageView>(R.id.toast_ic).setBackgroundResource(R.drawable.ic_baseline_error_24)
            ToastView.findViewById<TextView>(R.id.toast_content).setText(it)

            toast.view=ToastView
            toast.show()
        }
    }



}
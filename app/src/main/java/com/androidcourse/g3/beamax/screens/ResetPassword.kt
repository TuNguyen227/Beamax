package com.androidcourse.g3.beamax.screens

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.databinding.CustomeToastBinding
import com.androidcourse.g3.beamax.databinding.FragmentResetPasswordBinding



import com.google.firebase.auth.FirebaseAuth


class ResetPassword : Fragment() {
    private lateinit var binding: FragmentResetPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var toastbinding: CustomeToastBinding
    private lateinit var toastview:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentResetPasswordBinding.inflate(inflater,container,false)
        firebaseAuth= FirebaseAuth.getInstance()
        toastbinding= CustomeToastBinding.inflate(inflater,container,false)
        toastview = layoutInflater.inflate(R.layout.custome_toast, toastbinding.llcontainer,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_resetPassword_to_signIn)
        }

        binding.editTextEmail.setOnKeyListener setOnkeyListenr@{ view, i, keyEvent ->
            if (keyEvent.action==KeyEvent.ACTION_DOWN&& i==KeyEvent.KEYCODE_ENTER) {
                val email=binding.editTextEmail.text.toString().trim()
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        val toast= Toast(context)
                        toast.duration= Toast.LENGTH_SHORT
                        toastview.findViewById<ImageView>(R.id.toast_ic).setBackgroundResource(R.drawable.ic_baseline_error_24)
                        toastview.findViewById<TextView>(R.id.toast_content).setText("Please go to your email to check reset password link")

                        toast.view=toastview
                        toast.show()
                        binding.editTextEmail.clearFocus()
                    }
                }
                return@setOnkeyListenr true
            }
            else false

        }
    }


}
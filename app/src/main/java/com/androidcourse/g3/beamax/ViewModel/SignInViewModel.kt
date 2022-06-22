package com.androidcourse.group3.beamax.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.androidcourse.g3.beamax.Validators
import com.androidcourse.g3.beamax.base.BaseViewModel
import com.androidcourse.g3.beamax.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel(firebaseRepository: FirebaseRepository, handle: SavedStateHandle) : BaseViewModel(firebaseRepository, handle) {

    private  var firebaseAuth: FirebaseAuth
    private var signinLiveData= MutableLiveData<Boolean>()
    init {
        firebaseAuth= FirebaseAuth.getInstance()
    }


    val IsSignIn:LiveData<Boolean>
        get() = signinLiveData

    fun CheckingEmailPasswordForSignIn(email:String,password:String,repeatpassword:String) : Boolean
    {
        val isInputEmpty= Validators.checkemailpassword_input(email,password,repeatpassword)

        if (!isInputEmpty)
        {

            errorData.postValue("Please type your email and password")
            return false
        }

        return  isInputEmpty
    }

    fun SignIn(email: String,password: String)
    {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful)
            {
                if (firebaseAuth.currentUser?.isEmailVerified==true)
                    signinLiveData.postValue(true)
                else {
                    errorData.postValue("Unverified Account. Please go to your email and verify.")
                    signinLiveData.postValue(false)
                }

            }
        }

    }
}
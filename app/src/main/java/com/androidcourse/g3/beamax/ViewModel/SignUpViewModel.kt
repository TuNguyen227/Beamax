package com.androidcourse.group3.beamax.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidcourse.g3.beamax.Validators
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private var errorLiveData= MutableLiveData<String>()
    private var _sendEmailVerification=MutableLiveData<Boolean>()
    private  var firebaseAuth: FirebaseAuth

    init {
        firebaseAuth= FirebaseAuth.getInstance()
    }
    val error:LiveData<String>
        get() = errorLiveData

    val sendEmailVerification:LiveData<Boolean>
        get() = _sendEmailVerification



    fun CheckingEmailPasswordForSignUp(email:String,password:String,repeatpassword:String) : Boolean
    {
        val isEmailvalid=Validators.isEmailValid(email)
        val isPasswordLengthSatisfied=Validators.checkpasswordlength(password)
        val isPasswordPatternSatisfied=Validators.checkPasswordPatterns(password,repeatpassword)
        val isPasswordMatchRepeatpassword=Validators.checkPasswordMatchRepeatPassword(password,repeatpassword)
        val isInputEmpty=Validators.checkemailpassword_input(email,password,repeatpassword)

        if (!isInputEmpty)
        {
            errorLiveData.postValue("Please type your email and password")
            return false
        }

        if (!isEmailvalid)
        {
            errorLiveData.postValue("Please type a correct email format")
            return false
        }

        if (!isPasswordMatchRepeatpassword)
        {
            errorLiveData.postValue("Unmatched repeat password")
            return false
        }

        if (!isPasswordLengthSatisfied)
        {
            errorLiveData.postValue("Password limit must be at least 8 characters")
            return false
        }

        if (!isPasswordPatternSatisfied)
        {
            errorLiveData.postValue("Password must be contained character,A-Z,number and special character.")
            return false
        }





        return isEmailvalid && isPasswordLengthSatisfied && isPasswordPatternSatisfied && isPasswordMatchRepeatpassword && isInputEmpty
    }

    fun SignUP(email: String,password: String,repeatpassword: String)
    {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful)
            {
                firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                    _sendEmailVerification.postValue(true)
                }
            }
        }
    }

}
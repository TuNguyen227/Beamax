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

class SignUpViewModel(firebaseRepository: FirebaseRepository,
                      handle: SavedStateHandle
) : BaseViewModel(firebaseRepository, handle) {

    private var _sendEmailVerification=MutableLiveData<Boolean>()
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
            errorData.postValue("Please type your email and password")
            return false
        }

        if (!isEmailvalid)
        {
            errorData.postValue("Please type a correct email format")
            return false
        }

        if (!isPasswordMatchRepeatpassword)
        {
            errorData.postValue("Unmatched repeat password")
            return false
        }

        if (!isPasswordLengthSatisfied)
        {
            errorData.postValue("Password limit must be at least 8 characters")
            return false
        }

        if (!isPasswordPatternSatisfied)
        {
            errorData.postValue("Password must be contained character,A-Z,number and special character.")
            return false
        }
        return isEmailvalid && isPasswordLengthSatisfied && isPasswordPatternSatisfied && isPasswordMatchRepeatpassword && isInputEmpty
    }

    fun SignUP(email: String,password: String,repeatpassword: String)
    {
        firebaseRepository.getFirebaseAuth().createUserWithEmailAndPassword(email,password).addOnCompleteListener { it ->
            if (it.isSuccessful)
            {
                val currentUser=firebaseRepository.getCurrentUser()
                currentUser?.reload()
                if (!currentUser!!.isEmailVerified)
                {
                    firebaseRepository.getCurrentUser()?.sendEmailVerification()?.addOnCompleteListener {result->
                        if (result.isSuccessful)
                            _sendEmailVerification.postValue(true)
                        else
                            errorData.postValue("Could not create verification code")
                    }
                }

            }
        }
    }

}
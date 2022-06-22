package com.androidcourse.g3.beamax.ViewModel



import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.base.BaseViewModel
import com.androidcourse.g3.beamax.repository.FirebaseRepository
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.withContext


class WelcomeModel(firebaseRepository: FirebaseRepository, handle: SavedStateHandle,application: Application) :BaseViewModel(firebaseRepository, handle) {
    @SuppressLint("StaticFieldLeak")
    private val context=application.applicationContext
    private var success=MutableLiveData<Boolean>()
    val successLiveData:LiveData<Boolean>
        get() = success

    fun isAnonymousSignIn():Boolean
    {
        if (requestCurrentUser()!=null  && isAnonymousVerified() )
        {
            return true
        }
        return false
    }

    fun isAnonymousVerified():Boolean
    {
        if (requestCurrentUser()!=null)
        {
            for (profile in requestCurrentUser()!!.providerData)
            {
                if (profile.providerId!="facebook.com" && profile.providerId!="google")
                    if (profile.isEmailVerified)
                        return true
            }
        }

        return false
    }


    fun isGoogleClientSignin() : Boolean
    {
        if (requestCurrentUser()!=null  && isAccountProvidedbyGoogle() )
        {
            return true
        }
        return false
    }

    fun isFacebookClientSignin():Boolean
    {

        if (requestCurrentUser()!=null && isAccountProvidedbyFacebook() )
        {
            return true
        }
        return false
    }



    fun RegisterGoogleClientIDCredential(idToken : String?)
    {
        val credentical=GoogleAuthProvider.getCredential(idToken,null)
        FirebaseAuth.getInstance().signInWithCredential(credentical).addOnCompleteListener{

            if (it.isSuccessful)
            {
                success.postValue(true)

            }
            else
                errorData.postValue("Unsuccesfully")


        }

    }
    fun SetUpGoogleSignInClient(): GoogleSignInClient {
        var gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestEmail()
            .build()
        val Client=GoogleSignIn.getClient(context,gso)
        return Client
    }




}
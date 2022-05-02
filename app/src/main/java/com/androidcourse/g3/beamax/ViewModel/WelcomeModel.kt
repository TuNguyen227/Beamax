package com.androidcourse.g3.beamax.ViewModel



import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidcourse.g3.beamax.R
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class WelcomeModel(application:Application) :AndroidViewModel(application) {
    private  val firebaseAuth: FirebaseAuth

    private val context=getApplication<Application>().applicationContext



    private var error= MutableLiveData<String>()

    val errorLiveData:LiveData<String>
        get()=error

    private var success=MutableLiveData<Boolean>()
    val successLiveData:LiveData<Boolean>
        get() = success

    init {
        firebaseAuth= FirebaseAuth.getInstance()


    }
    fun isAnonymousSignIn(user: FirebaseUser?):Boolean
    {
        if (user!=null  && isAnonymousVerified(user) )
        {
            return true
        }
        return false
    }

    fun isAnonymousVerified(user: FirebaseUser?):Boolean
    {
        if (user!=null)
        {
            for (profile in user.providerData)
            {
                if (profile.providerId!="facebook.com" && profile.providerId!="google")
                    if (profile.isEmailVerified)
                        return true
            }
        }

        return false
    }


    fun isGoogleClientSignin(user: FirebaseUser?) : Boolean
    {


        if (user!=null  && isAccountProvidedbyGoogle(user) )
        {
            return true
        }
        return false
    }

    fun isAccountProvidedbyGoogle(user: FirebaseUser?):Boolean
    {
        if (user!=null)
        {
            for (profile in user.providerData)
            {
                if (profile.providerId=="google.com")
                    return true
            }
        }

        return false
    }

    fun isAccountProvidedbyFacebook(user: FirebaseUser?):Boolean
    {
        if (user!=null)
        {
            for (profile in user.providerData)
            {
                if (profile.providerId=="facebook.com")
                    return true
            }
        }

        return false
    }

    fun isFacebookClientSignin(user: FirebaseUser?):Boolean
    {

        if (user!=null && isAccountProvidedbyFacebook(user) )
        {
            return true
        }
        return false
    }



    fun RegisterGoogleClientIDCredential(idToken : String?)
    {
        val credentical=GoogleAuthProvider.getCredential(idToken,null)
        val userwithcredentical=firebaseAuth.signInWithCredential(credentical).addOnCompleteListener{

            if (it.isSuccessful)
            {
                success.postValue(true)

            }
            else
                error.postValue("Unsuccesfully")


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
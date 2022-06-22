package com.androidcourse.g3.beamax.base

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.androidcourse.g3.beamax.repository.FirebaseRepository
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser

abstract class BaseViewModel(protected val firebaseRepository: FirebaseRepository,protected val handle: SavedStateHandle): ViewModel(){
    protected var errorData=MutableLiveData<String>()
     val errorLiveData:LiveData<String>
        get() = errorData

    protected var isLoading=MutableLiveData<Boolean>()
     val isLoadingLiveData:LiveData<Boolean>
        get() = isLoading

    protected fun requestCurrentUser()=firebaseRepository.getCurrentUser()

    protected fun requestAvatarUri(): Uri? {
        var photoURL: Uri?=null
        if (isAccountProvidedbyGoogle() == true)
        {
            photoURL=requestCurrentUser()?.photoUrl
            return photoURL
        }
        if (isAccountProvidedbyFacebook()==true)
        {
            photoURL= Uri.parse("${requestCurrentUser()?.photoUrl}/picture?type=normal&access_token=${AccessToken.getCurrentAccessToken()?.token}")
            return photoURL
        }
        return photoURL
    }

    protected fun requestCurrentUserName()=requestCurrentUser()?.displayName

    protected fun isAccountProvidedbyGoogle():Boolean
    {
        if (requestCurrentUser()!=null)
        {
            for (profile in requestCurrentUser()!!.providerData)
            {
                if (profile.providerId=="google.com")
                    return true
            }
        }

        return false
    }

    protected fun isAccountProvidedbyFacebook():Boolean
    {
        if (requestCurrentUser()!=null)
        {
            for (profile in requestCurrentUser()!!.providerData)
            {
                if (profile.providerId=="facebook.com")
                    return true
            }
        }

        return false
    }

}
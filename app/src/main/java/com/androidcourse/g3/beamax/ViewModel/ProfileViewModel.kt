package com.androidcourse.g3.beamax.ViewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.androidcourse.g3.beamax.base.BaseViewModel
import com.androidcourse.g3.beamax.repository.FirebaseRepository
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileViewModel(firebaseRepository: FirebaseRepository, handle: SavedStateHandle) : BaseViewModel(firebaseRepository, handle){

    fun getAvatar()=requestAvatarUri()

    fun getProfileName()=requestCurrentUserName()
}
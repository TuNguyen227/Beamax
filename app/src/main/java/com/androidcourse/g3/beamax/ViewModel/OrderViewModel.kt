package com.androidcourse.g3.beamax.ViewModel

import androidx.lifecycle.SavedStateHandle
import com.androidcourse.g3.beamax.base.BaseViewModel
import com.androidcourse.g3.beamax.repository.FirebaseRepository

class OrderViewModel(firebaseRepository: FirebaseRepository,
                     handle: SavedStateHandle
) : BaseViewModel(firebaseRepository, handle) {
    fun getAvatar()=requestAvatarUri()
}
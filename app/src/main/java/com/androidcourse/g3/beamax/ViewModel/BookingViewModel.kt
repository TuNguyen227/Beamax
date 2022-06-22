package com.androidcourse.g3.beamax.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.androidcourse.g3.beamax.DATA.RequestBooking
import com.androidcourse.g3.beamax.base.BaseViewModel
import com.androidcourse.g3.beamax.repository.FirebaseRepository
import com.androidcourse.group3.beamax.DATA.Restaurants
import com.google.firebase.auth.FirebaseAuth

class BookingViewModel(firebaseRepository: FirebaseRepository,
                       handle: SavedStateHandle
):BaseViewModel(firebaseRepository, handle) {
    fun getAvatar()=requestAvatarUri()

    fun requestBooking(pos:String,date:String,time:String,peoples:String) {
        isLoading.postValue(true)
        val request=RequestBooking()
        request.data="${date}-${time}-${peoples}"
        request.id= firebaseRepository.getCurrentUser()?.uid
        firebaseRepository.getDatabase().child("Restaurants").child(pos).child("requestBooking").child(pos).setValue(request).addOnCompleteListener {
            if (it.isSuccessful)
                isLoading.postValue(false)
        }.addOnFailureListener {
            Log.d("debug",it.message.toString())
            isLoading.postValue(false)
        }
        }

    }

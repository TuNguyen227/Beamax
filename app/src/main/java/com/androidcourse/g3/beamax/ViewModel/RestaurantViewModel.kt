package com.androidcourse.g3.beamax.ViewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.androidcourse.g3.beamax.base.BaseViewModel
import com.androidcourse.g3.beamax.repository.FirebaseRepository
import com.androidcourse.group3.beamax.DATA.Restaurants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RestaurantViewModel(firebaseRepository: FirebaseRepository,
                          handle: SavedStateHandle
) : BaseViewModel(firebaseRepository, handle) {
    private var _restaurant=MutableLiveData<Restaurants?>()

    val restaurantLiveData:LiveData<Restaurants?>
    get()=_restaurant



    fun getRestaurant(name: String)
    {
        firebaseRepository.getDatabase()?.child("Restaurants")?.get()?.addOnCompleteListener {
            if (it.isSuccessful ){
                for (restaurant in it.result.children)
                {
                    val restaurantModel=restaurant.getValue(Restaurants::class.java)
                    if (restaurantModel?.name==name)
                        _restaurant.postValue(restaurantModel)
                }
            }
        }
    }

    fun getProfile(): Uri? {
        isLoading.postValue(true)
        val photoUri=requestAvatarUri()
        isLoading.postValue(false)
        return photoUri
    }
}
package com.androidcourse.g3.beamax.repository

import android.net.Uri
import android.util.Log
import com.androidcourse.g3.beamax.service.RestaurantService
import com.androidcourse.group3.beamax.DATA.Restaurants
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class FirebaseRepository() {
    private var firebaseAuth:FirebaseAuth
    private var firebaseDatabase:DatabaseReference
    fun getCurrentUser()=firebaseAuth.currentUser

    init {
        firebaseAuth=FirebaseAuth.getInstance()
        firebaseDatabase=FirebaseDatabase.getInstance("https://beamax-fe5f6-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    }
    fun getDatabase(): DatabaseReference {
        return firebaseDatabase
    }
    fun getFirebaseAuth(): FirebaseAuth {
        return firebaseAuth
    }


    fun requestRestaurant(response: ResponseListener)
    {
        getDatabase()?.child("Restaurants")?.get()?.addOnCompleteListener {
            if (it.isSuccessful)
            {
                val list= mutableListOf<Restaurants>()
                for(data in it.result.children)
                {
                    val restaurant=data.getValue(Restaurants::class.java)
                    list.add(restaurant!!)
                }
                response.onReceivedResponse(list)
            }
            else
                response.onError(it.exception.toString())
        }
    }

}
interface ResponseListener{
    fun onReceivedResponse(list: List<Restaurants?>)
    fun onError(e : String)
    fun onSuccess(task:Boolean)
}
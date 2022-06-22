package com.androidcourse.g3.beamax.ViewModel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.androidcourse.g3.beamax.base.BaseViewModel
import com.androidcourse.g3.beamax.repository.FirebaseRepository
import com.androidcourse.g3.beamax.repository.ResponseListener
import com.androidcourse.group3.beamax.DATA.Restaurants
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.coroutines.*

class HomeViewModel(firebaseRepository: FirebaseRepository, handle: SavedStateHandle) : BaseViewModel(firebaseRepository, handle) {

    private var listData=MutableLiveData<List<Restaurants?>>()
    private var listOfRestaurant= mutableListOf<Restaurants?>()
    private var onSearchList= mutableListOf<Restaurants?>()
    val _listLiveda:LiveData<List<Restaurants?>>
        get() = listData


    fun getAvatar()=requestAvatarUri()
    fun onSearchDataChanged(searchText: String?)
    {
        isLoading.postValue(true)
        onSearchList.clear()
        if (searchText != null) {
            Log.d("debug",searchText)
            listOfRestaurant.forEach{
                if (it?.name?.lowercase()?.contains(searchText)==true)
                {
                    onSearchList.add(it)
                    Log.d("debug_list",onSearchList.size.toString())
                }
            }
            listData.postValue(onSearchList)
            isLoading.postValue(false)
        }
        else
        {
            onSearchList.clear()

            listData.postValue(onSearchList)
            isLoading.postValue(false)
        }
    }

    fun getListData()
    {
            isLoading.postValue(true)
        firebaseRepository.requestRestaurant(object : ResponseListener{
                override fun onReceivedResponse(list: List<Restaurants?>) {
                    listData.postValue(list)
                    listOfRestaurant=list.toMutableList()
                    isLoading.postValue(false)
                }

                override fun onError(e: String) {
                    isLoading.postValue(false)
                }

            override fun onSuccess(task: Boolean) {

            }
        } )
    }

    fun getListDataByCategory(requestcategory:String)
    {
        isLoading.postValue(true)
        if (requestcategory.equals("All"))
        {
            listData.postValue(listOfRestaurant)
            isLoading.postValue(false)
            return
        }
        onSearchList.clear()
        Log.d("currentList",onSearchList.size.toString())
        firebaseRepository.requestRestaurant(object : ResponseListener{
            override fun onReceivedResponse(list: List<Restaurants?>) {
                list.forEach {
                    if(it?.category.toString().contains(requestcategory))
                        onSearchList.add(it)
                    if(requestcategory.equals("Booking")&& it?.hasBooking==true)
                        onSearchList.add(it)

                }
                Log.d("currentList",onSearchList.size.toString())
                listData.postValue(onSearchList)
                isLoading.postValue(false)
            }

            override fun onError(e: String) {
                isLoading.postValue(false)
            }
            override fun onSuccess(task: Boolean) {
            }
        })
    }

    }








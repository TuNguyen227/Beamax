package com.androidcourse.g3.beamax.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.androidcourse.g3.beamax.DATA.Dish
import com.androidcourse.g3.beamax.base.BaseViewModel
import com.androidcourse.g3.beamax.repository.FirebaseRepository
import com.androidcourse.g3.beamax.repository.ResponseListener
import com.androidcourse.group3.beamax.DATA.Restaurants

class MenuViewModel(firebaseRepository: FirebaseRepository,
                    handle: SavedStateHandle
) : BaseViewModel(firebaseRepository, handle) {
    private var dishData=MutableLiveData<List<Dish>>()
    val dishLiveData:LiveData<List<Dish>>
            get() = dishData
    fun fetchDish(restaurant:String)
    {
        firebaseRepository.requestRestaurant(object :ResponseListener{
            override fun onReceivedResponse(list: List<Restaurants?>) {
                list.forEach {
                    if (it?.name==restaurant)
                    {
                        dishData.postValue(it.dish)
                    }
                }
            }

            override fun onError(e: String) {

            }

            override fun onSuccess(task: Boolean) {

            }
        })
    }
    fun getAvatar()=requestAvatarUri()
}
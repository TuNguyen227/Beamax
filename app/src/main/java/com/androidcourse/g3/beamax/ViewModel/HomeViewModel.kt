package com.androidcourse.g3.beamax.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidcourse.group3.beamax.DATA.User
import com.google.firebase.database.*
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var listData=MutableLiveData<List<User>>()
    private var listUser= mutableListOf<User>()
    private var onSearchList= mutableListOf<User>()
    private lateinit var database : DatabaseReference
    val _listLiveda:LiveData<List<User>>
        get() = listData
    init {
        database=FirebaseDatabase.getInstance("https://beamax-fe5f6-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    }

    fun getListformDatabase()
    {
        Log.d("debug","get data")
        viewModelScope.launch {
            database.child("Users").get().addOnSuccessListener {
                for (data in it.children) {
                    val user = data.getValue(User::class.java)
                    if (user != null) {
                        listUser.add(user)
                    }
                }
            }.addOnCompleteListener {
                listData.postValue(listUser)

            }
        }




    }

    fun onSearchDataChanged(searchText: String?)
    {
        onSearchList.clear()
        if (searchText != null) {
            Log.d("debug",searchText)
            listUser.forEach{
                if (it.name.lowercase().contains(searchText))
                {
                    onSearchList.add(it)
                    Log.d("debug_list",onSearchList.size.toString())
                }
            }
            listData.postValue(onSearchList)

        }
        else
        {
            onSearchList.clear()

            listData.postValue(onSearchList)
        }
    }

    fun NotifyDataChanged()
    {


        database.child("Users").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    listUser.clear()
                    Log.d("debug","data changed")
                    for (data in snapshot.children)
                    {
                        val user=data.getValue(User::class.java)

                        if (user != null) {

                            listUser.add(user)
                        }


                    }
                    listData.postValue(listUser)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("debug6",error.message)
            }


        })
    }
}
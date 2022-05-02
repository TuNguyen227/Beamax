package com.androidcourse.g3.beamax.screens

import android.annotation.SuppressLint
import com.androidcourse.g3.beamax.R
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidcourse.g3.beamax.ViewModel.HomeViewModel
import com.androidcourse.g3.beamax.adapter.HomeAdapter
import com.androidcourse.g3.beamax.databinding.FragmentHomeBinding
import com.androidcourse.g3.beamax.databinding.ItemBinding
import com.androidcourse.group3.beamax.DATA.User

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*


class Home : Fragment() {
    private lateinit var bing: FragmentHomeBinding
    private lateinit var itemBinding: ItemBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var database : DatabaseReference
    private lateinit var homeViewModel: HomeViewModel
    private var original_list= mutableListOf<User>()
    private var onlistSearch= mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bing= FragmentHomeBinding.inflate(inflater,container,false)
        firebaseAuth= FirebaseAuth.getInstance()
        itemBinding= ItemBinding.inflate(inflater,container,false)
        homeViewModel=ViewModelProvider(this).get(HomeViewModel::class.java)
        database=FirebaseDatabase.getInstance("https://beamax-fe5f6-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        return bing.root
    }

    override fun onStart() {
        super.onStart()
        registerUserList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bing.signinBtn.setOnClickListener {
            firebaseAuth.signOut()
            findNavController().navigate(R.id.action_home2_to_welcome)
        }

        bing.searchview.setOnQueryTextFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(view: View?, isFocus: Boolean) {
                bing.searchview.isSelected= isFocus
                bing.searchview.isIconified=!isFocus
            }
        })
        bing.searchview.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText=newText?.lowercase()
                homeViewModel.onSearchDataChanged(searchText)

                return true
            }
        })

        setUpListForUI()

        homeViewModel.NotifyDataChanged()

    }

    fun setUpListForUI(){
        val layout=LinearLayoutManager(context)
        homeAdapter=HomeAdapter()
        bing.rv.adapter=homeAdapter

        bing.rv.layoutManager=layout

    }
    fun registerUserList()
    {
        homeViewModel._listLiveda.observe(viewLifecycleOwner)
        {


            homeAdapter.submitList(it?.toMutableList())


        }
    }





}
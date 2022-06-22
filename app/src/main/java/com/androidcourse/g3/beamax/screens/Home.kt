package com.androidcourse.g3.beamax.screens


import android.annotation.SuppressLint
import com.androidcourse.g3.beamax.R
import android.os.Bundle
import android.util.Log
import android.view.*

import android.widget.ProgressBar

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidcourse.g3.beamax.DATA.Categories
import com.androidcourse.g3.beamax.ViewModel.HomeViewModel
import com.androidcourse.g3.beamax.adapter.CategoryAdapter
import com.androidcourse.g3.beamax.adapter.HomeAdapter
import com.androidcourse.g3.beamax.utility.AnimationUtil

import com.androidcourse.g3.beamax.base.BaseFragment
import com.androidcourse.g3.beamax.databinding.FragmentHomeBinding

import com.androidcourse.g3.beamax.interfaces.OnCategoryClickListener
import com.androidcourse.g3.beamax.interfaces.OnItemClickListener
import com.androidcourse.group3.beamax.DATA.Restaurants
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import org.koin.androidx.viewmodel.ext.android.stateViewModel


class Home : BaseFragment() {
    private lateinit var bing: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private  val homeViewModel: HomeViewModel by stateViewModel()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var layout:LinearLayoutManager
    private var isAnimationFinished=true
    private var isSearching:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun init() {
        homeViewModel.getListData()
        registerUserList()
        registerCategoriesList()
    }

    override fun setUpUI() {
        setUpListForUI()
        setUpUICategories()
        setUpHomeProfileAvatar()
    }

    override fun setListener() {
        bing.profileHome.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_profile)
        }

        bing.searchbox.setOnQueryTextFocusChangeListener { v, hasFocus ->
            bing.searchbox.isSelected = hasFocus
        }

        bing.searchbox.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                homeViewModel.onSearchDataChanged(p0?.lowercase())
                return true
            }
        })
        bing.searchbox.setOnQueryTextFocusChangeListener { v, hasFocus ->
            when(hasFocus){
                true -> AnimationUtil.collapse(bing.rvCategory)
                else -> AnimationUtil.expand(bing.rvCategory)
            }
        }
        onScrollingListener()
    }

    override fun setObserver() {
        onLoadingListener()
    }

    override fun setAnimation() {
        bing.rv.apply {
            itemAnimator=null
        }
        bing.rvCategory.apply {
            itemAnimator=null
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bing= FragmentHomeBinding.inflate(inflater,container,false)
        return bing.root
    }


    fun setUpListForUI(){
        layout=LinearLayoutManager(context)
        homeAdapter= HomeAdapter(object :OnItemClickListener{
            override fun onItemClick(restaurants: Restaurants, pos: Int) {
                val bundle=Bundle()
                bundle.putString("DATA","${restaurants.name}.${pos}")
                findNavController().navigate(R.id.action_home2_to_restaurantScreen,bundle)
            }
        })
        bing.rv.adapter=homeAdapter

        bing.rv.layoutManager=layout

    }
    @SuppressLint("ClickableViewAccessibility")
    fun onScrollingListener()
    {
        bing.rv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if ( dy>0 && isAnimationFinished && bing.rvCategory.visibility!=View.GONE)
                {
                    AnimationUtil.collapse(bing.rvCategory)

                }
                if (layout.findFirstCompletelyVisibleItemPosition() ==0 && bing.rvCategory.visibility!=View.VISIBLE)
                    AnimationUtil.expand(bing.rvCategory)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }
    fun registerUserList()
    {
        homeViewModel._listLiveda.observe(viewLifecycleOwner)
        {

            homeAdapter.submitList(it?.toMutableList())
            Log.d("aaaa",it?.size.toString())

        }
    }


    fun setUpUICategories()
    {

            val gridlayout=GridLayoutManager(context,3)
        categoryAdapter= CategoryAdapter(object :OnCategoryClickListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onCategoryClick(category: Categories) {
                homeViewModel.getListDataByCategory(category.name)
                homeAdapter.notifyDataSetChanged()
            }

            override fun onItemClick(restaurants: Restaurants, pos: Int) {

            }
        })
            bing.rvCategory.adapter=categoryAdapter
            bing.rvCategory.layoutManager=gridlayout

    }

    fun registerCategoriesList()
    {
        categoryAdapter.submitList(Categories.getCategoryList())
    }

    fun setUpHomeProfileAvatar()
    {
        val requestOptions= RequestOptions
            .centerCropTransform()
            .override(200,200)
            .error(R.drawable.ic_baseline_error_24)
            .placeholder(R.drawable.ic_baseline_mail_24)
            Glide.with(requireView().context).load(homeViewModel.getAvatar()).apply(requestOptions).into(bing.profileHome)
    }
    fun onLoadingListener()
    {
        homeViewModel.isLoadingLiveData.observe(viewLifecycleOwner){
            if (it)
                bing.progBar.visibility=ProgressBar.VISIBLE
            else
                bing.progBar.visibility=ProgressBar.GONE
        }
    }




}
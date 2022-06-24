package com.androidcourse.g3.beamax.screens

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.androidcourse.g3.beamax.DATA.DATE
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.ViewModel.RestaurantViewModel
import com.androidcourse.g3.beamax.base.BaseFragment
import com.androidcourse.g3.beamax.databinding.CustomeToastBinding
import com.androidcourse.g3.beamax.databinding.FragmentRestaurantScreenBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import java.util.*

class RestaurantScreen : BaseFragment() {
    private lateinit var binding:FragmentRestaurantScreenBinding
    private  val restaurantViewModel: RestaurantViewModel by stateViewModel()
    private var hasBooking:Boolean?=null
    private lateinit var toastview:View
    private lateinit var toastbinding: CustomeToastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private val list by lazy {
        arguments?.getString("DATA")?.split(".")
    }

    override fun init() {
        restaurantViewModel.getRestaurant(list?.get(0) ?: String())
    }

    override fun setUpUI() {
        setUpProfileUI()
    }

    override fun setListener() {
        onBackListener()
        onGoToProfileListener()
        onBookingListener()
        onMenuListener()
    }

    override fun setObserver() {
        onLoadingDataObserver()
        onGettingDataObserver()
    }

    override fun setAnimation() {

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentRestaurantScreenBinding.inflate(inflater,container,false)
        toastbinding= DataBindingUtil.inflate(inflater, R.layout.custome_toast,container,false)
        toastview = layoutInflater.inflate(R.layout.custome_toast, toastbinding.llcontainer,false)
        return binding.root
    }


    fun setUpUI(photo: Uri,name:String)
    {
        binding.progBar.visibility=View.VISIBLE
        view?.let { Glide.with(it.context).load(photo).listener(object : RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                binding.progBar.visibility=View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                binding.progBar.visibility=View.GONE
                return  false
            }
        }).into(binding.mainPhoto) }
        binding.restaurantName.text=name
    }

    fun onGettingDataObserver()
    {
        restaurantViewModel.restaurantLiveData.observe(viewLifecycleOwner){
            setUpUI(Uri.parse(it?.mainphotoURL),it?.name.toString())
            hasBooking=it?.hasBooking
        }
    }

    fun onLoadingDataObserver()
    {
        restaurantViewModel.isLoadingLiveData.observe(viewLifecycleOwner){
            if (it)
                binding.rsProBar.visibility=View.VISIBLE
            else
                binding.rsProBar.visibility=View.GONE
        }
    }

    fun setUpProfileUI()
    {
        binding.rsProBar.visibility=View.VISIBLE
        view?.let { Glide.with(it.context).load(restaurantViewModel.getProfile()).into(binding.rsImgProfile) }
    }

    fun onBackListener()
    {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    fun onGoToProfileListener()
    {
        binding.rsImgProfile.setOnClickListener {
            findNavController().navigate(R.id.action_restaurantScreen_to_profile)
        }
    }

    fun onBookingListener()
    {
        binding.booking.setOnClickListener {
            if (hasBooking==true) {
                val bundle=Bundle()
                bundle.putString("DATA",arguments?.getString("DATA"))
                findNavController().navigate(R.id.action_restaurantScreen_to_bookingScreen,bundle)

            }
            else
            {
                val toast= Toast(context)
                toast.duration= Toast.LENGTH_SHORT
                toastview.findViewById<ImageView>(R.id.toast_ic).setBackgroundResource(R.drawable.ic_baseline_notification_important_24)
                toastview.findViewById<TextView>(R.id.toast_content).setText("Opps!! We do not support booking service")
                toast.view=toastview
                toast.show()
            }

        }

    }
    fun onMenuListener()
    {
        binding.menu.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("DATA_FD",arguments?.getString("DATA"))
            findNavController().navigate(R.id.action_restaurantScreen_to_menuScreen,bundle)
        }
    }




}
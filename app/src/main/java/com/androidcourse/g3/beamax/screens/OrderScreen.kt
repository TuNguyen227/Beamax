package com.androidcourse.g3.beamax.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.ViewModel.OrderViewModel
import com.androidcourse.g3.beamax.base.BaseFragment
import com.androidcourse.g3.beamax.databinding.FragmentBookingScreenBinding
import com.androidcourse.g3.beamax.databinding.FragmentOrderScreenBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.stateViewModel


class OrderScreen : BaseFragment() {
    private val orderViewModel:OrderViewModel by stateViewModel()
    private lateinit var binding:FragmentOrderScreenBinding
    override fun init() {

    }
    private val list by lazy {

    }

    override fun setUpUI() {
        setUpProfileUI()
    }

    override fun setListener() {

    }

    override fun setObserver() {

    }

    override fun setAnimation() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentOrderScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    fun setUpProfileUI()
    {
        val requestOptions= RequestOptions
            .centerCropTransform()
            .override(200,200)
            .error(R.drawable.ic_baseline_error_24)
            .placeholder(R.drawable.ic_baseline_mail_24)
        Glide.with(requireView().context).load(orderViewModel.getAvatar()).apply(requestOptions).into(binding.profileImg)
    }

}
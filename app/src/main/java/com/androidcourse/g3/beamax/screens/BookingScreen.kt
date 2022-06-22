package com.androidcourse.g3.beamax.screens

import android.app.TimePickerDialog
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.format.DateFormat.format
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidcourse.g3.beamax.DATA.DATE
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.ViewModel.BookingViewModel
import com.androidcourse.g3.beamax.adapter.DateAdapter
import com.androidcourse.g3.beamax.base.BaseFragment
import com.androidcourse.g3.beamax.databinding.FragmentBookingScreenBinding
import com.androidcourse.g3.beamax.interfaces.OnDateItemClick
import com.androidcourse.g3.beamax.utility.DayTimeUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.internal.bind.util.ISO8601Utils.format
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import java.lang.String.format
import java.text.MessageFormat.format
import java.util.*
import kotlin.math.log

class BookingScreen : BaseFragment() {
    private val bookingViewModel:BookingViewModel by stateViewModel()
    private lateinit var adapter: DateAdapter
    private lateinit var binding: FragmentBookingScreenBinding
    private var date:String?=null
    private var time:String?=null
    private var peoples:String?=null
    private val bundle by lazy{
        Bundle()
    }

    private val list by lazy{
        arguments?.getString("DATA")?.split(".")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun init() {

    }

    override fun setUpUI() {
        setUpRecyclerViewUI()
        setUpProfileUI()
    }

    override fun setListener() {
        binding.timePicker.setOnClickListener {
            context?.let { it1 -> DayTimeUtil.getTimerPicker(it1,binding.timePicker) }
            time=binding.timePicker.text.toString()
        }
        binding.txt12Peo.setOnClickListener {
            binding.txt12Peo.setBackgroundResource(R.drawable.date_clicked_shape)
            binding.txt24Peo.setBackgroundResource(R.drawable.date_unclick_shape)
            binding.txt46Peo.setBackgroundResource(R.drawable.date_unclick_shape)
            peoples=binding.txt12Peo.text.toString()
        }
        binding.txt24Peo.setOnClickListener {
            binding.txt24Peo.setBackgroundResource(R.drawable.date_clicked_shape)
            binding.txt12Peo.setBackgroundResource(R.drawable.date_unclick_shape)
            binding.txt46Peo.setBackgroundResource(R.drawable.date_unclick_shape)
            peoples=binding.txt24Peo.text.toString()
        }
        binding.txt46Peo.setOnClickListener {
            binding.txt46Peo.setBackgroundResource(R.drawable.date_clicked_shape)
            binding.txt24Peo.setBackgroundResource(R.drawable.date_unclick_shape)
            binding.txt12Peo.setBackgroundResource(R.drawable.date_unclick_shape)
            peoples=binding.txt46Peo.text.toString()
        }
        binding.btnBooking.setOnClickListener {
            if (date!=null && time!=null && peoples !=null)
                bookingViewModel.requestBooking(list?.get(1) ?: String(), date!!, time!!, peoples!!)
        }
        binding.profileImg.setOnClickListener {
            findNavController().navigate(R.id.action_bookingScreen_to_profile)
        }
        binding.btnBack.setOnClickListener {
            findNavController().enableOnBackPressed(true)
        }
    }

    override fun setObserver() {
        bookingViewModel.isLoadingLiveData.observe(viewLifecycleOwner){
            if (it)
                binding.progBar.visibility=View.VISIBLE
            else
                binding.progBar.visibility=View.GONE
        }
    }

    override fun setAnimation() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentBookingScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    fun setUpRecyclerViewUI()
    {
        adapter=DateAdapter(object :OnDateItemClick{
            override fun onItemClick(date: DATE, position: Int) {
                for (pos in 0..(binding.rv.childCount-1))
                {
                    if (pos == position)
                    {
                        this@BookingScreen.date=position.toString()
                        bundle.putString("date","${date.date}")
                        binding.rv.getChildAt(pos).setBackgroundResource(R.drawable.date_clicked_shape)
                    }
                    else
                        binding.rv.getChildAt(pos).setBackgroundResource(R.drawable.date_unclick_shape)
                }
            }
        })
        binding.rv.adapter=adapter

        binding.rv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        adapter.submitList(DayTimeUtil.getDaysOfMonth())
    }

    fun setUpProfileUI()
    {
        val requestOptions= RequestOptions
            .centerCropTransform()
            .override(200,200)
            .error(R.drawable.ic_baseline_error_24)
            .placeholder(R.drawable.ic_baseline_mail_24)
        Glide.with(requireView().context).load(bookingViewModel.getAvatar()).apply(requestOptions).into(binding.profileImg)
    }

}
package com.androidcourse.g3.beamax.screens

import android.annotation.SuppressLint
import android.graphics.Camera
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidcourse.g3.beamax.DATA.Dish
import com.androidcourse.g3.beamax.DATA.OrderDish
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.ViewModel.MenuViewModel
import com.androidcourse.g3.beamax.adapter.DishAdapter
import com.androidcourse.g3.beamax.adapter.OnDishClickListener
import com.androidcourse.g3.beamax.base.BaseFragment
import com.androidcourse.g3.beamax.databinding.CustomeToastBinding
import com.androidcourse.g3.beamax.databinding.FragmentMenuScreenBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.w3c.dom.Text


class MenuScreen : BaseFragment() {
    private lateinit var binding:FragmentMenuScreenBinding
    private lateinit var toastbinding: CustomeToastBinding
    private lateinit var map: GoogleMap
    private lateinit var toastview:View
    private lateinit var adapter: DishAdapter
    private val listOfOrder= mutableListOf<OrderDish>()
    private val menuViewModel: MenuViewModel by stateViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val list by lazy{
        arguments?.getString("DATA_FD")?.split(".")
    }

    override fun init() {
        menuViewModel.fetchDish(list?.get(0) ?: String())

    }

    override fun setUpUI() {

            adapter=DishAdapter(object :OnDishClickListener{
                override fun onItemClick(dish: Dish, btn: ImageButton,number: TextView) {
                    when(btn.contentDescription)
                    {
                        "add" -> { val count=(number.text.toString().toInt()+1)
                                        number.text=count.toString()
                        }
                        "remove" -> { val count=(number.text.toString().toInt()-1)
                                            number.text=if(count>0) count.toString() else 0.toString()
                        }
                        else -> return
                    }

                }
            })
        binding.rv.adapter=adapter
        binding.rv.layoutManager=LinearLayoutManager(context)

        setUpProfileUI()
    }

    @SuppressLint("CutPasteId")
    override fun setListener() {
        binding.locationPicker.setOnClickListener {

        }

        binding.profileImg.setOnClickListener {
            findNavController().navigate(R.id.action_menuScreen_to_profile)
        }
        binding.btnOrder.setOnClickListener {
            listOfOrder.clear()
            for(i in 0 until (adapter.itemCount))
            {
                val itemview=binding.rv.getChildAt(i).findViewById(R.id.dish_item_layout) as View
                val dish=(itemview.findViewById(R.id.dish_name) as TextView).text.toString()
                val price=(itemview.findViewById(R.id.dish_price) as TextView).text.toString()
                val number=(itemview.findViewById(R.id.number) as TextView).text.toString()
                if (number!="0")
                    listOfOrder.add(OrderDish(dish,price,number))
            }
            val toast= Toast(context)
            toast.duration= Toast.LENGTH_SHORT


            toast.view=toastview

            if (list?.size!=0)
            {
                var total:Long=0
                listOfOrder.forEach{
                    total=total + (it.price!!.toLong()*it.number!!.toLong())
                }
                toastview.findViewById<ImageView>(R.id.toast_ic).setBackgroundResource(R.drawable.ic_baseline_check_24)
                val text="You have to pay ${total} VND."
                toastview.findViewById<TextView>(R.id.toast_content).setText(text)
                toast.view=toastview
                toast.show()
            }
            else
            {
                toastview.findViewById<ImageView>(R.id.toast_ic).setBackgroundResource(R.drawable.ic_baseline_notification_important_24)
                toastview.findViewById<TextView>(R.id.toast_content).setText("You have not odered yet")
                toast.view=toastview
                toast.show()
            }


        }
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setObserver() {
        menuViewModel.dishLiveData.observe(viewLifecycleOwner){
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun setAnimation() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMenuScreenBinding.inflate(inflater,container,false)
        toastbinding= DataBindingUtil.inflate(inflater, R.layout.custome_toast,container,false)
        toastview = layoutInflater.inflate(R.layout.custome_toast, toastbinding.llcontainer,false)
//        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this@MenuScreen)
        return binding.root
    }

//    override fun onMapReady(p0: GoogleMap) {
//        map=p0
//        val DDL=LatLng(10.778290044128898, 106.69582306442874)
//        map.addMarker(MarkerOptions().position(LatLng(10.778290044128898, 106.69582306442874)).title("Dinh Doc Lap"))
//        map.moveCamera(CameraUpdateFactory.newLatLng(DDL))
//    }
fun setUpProfileUI()
{
    val requestOptions= RequestOptions
        .centerCropTransform()
        .override(200,200)
        .error(R.drawable.ic_baseline_error_24)
        .placeholder(R.drawable.ic_baseline_mail_24)
    Glide.with(requireView().context).load(menuViewModel.getAvatar()).apply(requestOptions).into(binding.profileImg)
}


}
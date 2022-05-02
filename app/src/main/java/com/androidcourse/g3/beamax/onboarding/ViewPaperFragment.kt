package com.androidcourse.g3.beamax.onboarding

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.androidcour.onboardingAdapter
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.databinding.FragmentViewPaperBinding
import com.androidcourse.g3.beamax.screens.onboarding1
import com.androidcourse.g3.beamax.screens.onboarding2
import com.androidcourse.g3.beamax.screens.onboarding3


import me.relex.circleindicator.CircleIndicator3


class ViewPaperFragment : Fragment() {
    private lateinit var binding: FragmentViewPaperBinding
    private lateinit var circleIndicator: CircleIndicator3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_view_paper,container,false)
        val fragmentList= arrayListOf<Fragment>(
            onboarding1(),
            onboarding2(),
            onboarding3(),
        )
        val adapter=onboardingAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)
        val viewpaper=binding.viewpaper
        viewpaper.adapter=adapter
        circleIndicator=binding.dot
        circleIndicator.setViewPager(viewpaper)

        adapter.registerAdapterDataObserver(circleIndicator.adapterDataObserver)
        return binding.root
    }



}




package com.androidcourse.g3.beamax.onboarding

import android.os.Bundle
import android.util.Log
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
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class ViewPaperFragment : Fragment() {
    private lateinit var binding: FragmentViewPaperBinding
    private val fragmentList= arrayListOf(
        onboarding1.getInstance(),
        onboarding2.getInstance(),
        onboarding3.getInstance()
    )
    private lateinit var circleIndicator: CircleIndicator3
    private val adapter:onboardingAdapter by inject{ parametersOf(fragmentList,parentFragmentManager,lifecycle)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_view_paper,container,false)
        binding.viewpaper.adapter=adapter
        circleIndicator=binding.dot
        circleIndicator.setViewPager(binding.viewpaper)

        adapter.registerAdapterDataObserver(circleIndicator.adapterDataObserver)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}




package com.androidcourse.g3.beamax.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    protected abstract fun init()
    protected abstract fun setUpUI()
    protected abstract fun setListener()
    protected abstract fun setObserver()
    protected abstract fun setAnimation()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        init()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setObserver()
        setListener()
        setAnimation()
    }
}
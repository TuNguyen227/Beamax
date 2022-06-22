package com.androidcourse.g3.beamax.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.ViewModel.ProfileViewModel
import com.androidcourse.g3.beamax.base.BaseFragment
import com.androidcourse.g3.beamax.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel


class Profile : BaseFragment() {

    private lateinit var binding:FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by stateViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun init() {

    }

    override fun setUpUI() {
        setUpAvatarUI()
        setUpProfileName()
    }

    override fun setListener() {
        binding.signoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().apply {
                navigate(R.id.action_profile_to_welcome)
            }
        }
        binding.backBtn.setOnClickListener {

        }
    }

    override fun setObserver() {

    }

    override fun setAnimation() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    fun setUpAvatarUI()
    {
        val requestOptions= RequestOptions
            .centerCropTransform()
            .override(300,300)
            .error(R.drawable.ic_baseline_error_24)
            .placeholder(R.drawable.ic_baseline_mail_24)

        context?.let { Glide.with(it).load(profileViewModel.getAvatar()).apply(requestOptions).into(binding.avatar) }
    }

    fun setUpProfileName()
    {
        binding.profileName.text=profileViewModel.getProfileName()
    }







}
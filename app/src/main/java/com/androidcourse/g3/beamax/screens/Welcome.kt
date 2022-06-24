package com.androidcourse.g3.beamax.screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.ViewModel.WelcomeModel
import com.androidcourse.g3.beamax.base.BaseFragment
import com.androidcourse.g3.beamax.databinding.FragmentWelcomeBinding


import com.facebook.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import java.util.*

class Welcome : BaseFragment() {
    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSigninClient: GoogleSignInClient
    private val welcomeModel: WelcomeModel by stateViewModel()
    private lateinit var callbackManager: CallbackManager
   private lateinit var firebaseAuth_listener: FirebaseAuth.AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun init() {
        googleSigninClient= welcomeModel.SetUpGoogleSignInClient()
    }

    override fun setUpUI() {

    }

    override fun setListener() {
        binding.fbBtn.setOnClickListener {
            binding.progBar.visibility=View.VISIBLE
            LoginManager.getInstance().logOut()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"))

        }

        binding.signinBtn.setOnClickListener {
            findNavController().navigate(R.id.action_welcome_to_signIn)
        }
        binding.ggBtn.setOnClickListener {
            binding.progBar.visibility=View.VISIBLE
            googleSigninClient.signOut()
            openFragmentforResult()

        }
    }

    override fun setObserver() {
        signIn_error_listener()
        SignIn_success_listener()
        firebaseAuth.addAuthStateListener(firebaseAuth_listener)
        LoginManager.getInstance().registerCallback(callbackManager,object : FacebookCallback<LoginResult>
        {
            override fun onCancel() {
                binding.progBar.visibility=View.GONE
            }

            override fun onError(error: FacebookException) {
                binding.progBar.visibility=View.GONE
            }

            override fun onSuccess(result: LoginResult) {
                handleFacebookaccessToken(result.accessToken)
            }
        })
    }

    override fun setAnimation() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentWelcomeBinding.inflate(inflater,container,false)
        firebaseAuth= FirebaseAuth.getInstance()
        callbackManager= CallbackManager.Factory.create()
        FacebookSdk.fullyInitialize()

        firebaseAuth_listener=FirebaseAuth.AuthStateListener {
            if (welcomeModel.isGoogleClientSignin() || welcomeModel.isFacebookClientSignin() || welcomeModel.isAnonymousSignIn())
            {
                view?.post {
                    findNavController().navigate(R.id.action_welcome_to_home2)
                }


            }
        }
        return binding.root
    }

    private fun handleFacebookaccessToken(accessToken: AccessToken) {
        val credential=FacebookAuthProvider.getCredential(accessToken.token)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful)
            {

                binding.progBar.visibility=View.GONE

            }
        }.addOnFailureListener{
            Log.d("debugggg",it.message.toString())
            binding.progBar.visibility=View.GONE
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }

    fun openFragmentforResult()
    {
        val intent=googleSigninClient.signInIntent

        resultLauncher.launch(intent)
        Log.d("debug","2")
    }
    var resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {

        Log.d("debug",it.resultCode.toString() +" " + Activity.RESULT_OK)


            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            Log.d("debug","4")
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)
                    welcomeModel.RegisterGoogleClientIDCredential(account?.idToken)
                    binding.progBar.visibility=View.GONE
                    Log.d("debug","6")
                } catch (e: ApiException) {
                    Log.d("SignIn", e.toString())
                }
            } else
            {
                binding.progBar.visibility=View.GONE
                Log.d("Signin", task.exception.toString())
            }



    }



    private fun SignIn_success_listener()
    {
        welcomeModel.successLiveData.observe(viewLifecycleOwner){
            if (it==true)
            {
                findNavController().navigate(R.id.action_welcome_to_home2)
                Log.d("debug","6")
            }
        }


    }

    private fun signIn_error_listener()
    {
        welcomeModel.errorLiveData.observe(viewLifecycleOwner){
            Toast.makeText(context,it,Toast.LENGTH_SHORT).show()

        }
    }




}
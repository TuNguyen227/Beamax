package com.androidcourse.g3.beamax.screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.ViewModel.WelcomeModel
import com.androidcourse.g3.beamax.databinding.FragmentWelcomeBinding


import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.UserInfo
import java.util.*

class Welcome : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSigninClient: GoogleSignInClient
    private lateinit var welcomeModel: WelcomeModel
    private lateinit var callbackManager: CallbackManager
   private lateinit var firebaseAuth_listener: FirebaseAuth.AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentWelcomeBinding.inflate(inflater,container,false)
        welcomeModel=ViewModelProvider(this).get(WelcomeModel::class.java)
        firebaseAuth= FirebaseAuth.getInstance()
        callbackManager= CallbackManager.Factory.create()
        FacebookSdk.sdkInitialize(requireContext())




        firebaseAuth_listener=FirebaseAuth.AuthStateListener {


            if (welcomeModel.isGoogleClientSignin(it.currentUser) || welcomeModel.isFacebookClientSignin(it.currentUser) || welcomeModel.isAnonymousSignIn(it.currentUser))
            {
                view?.post{
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

                if(firebaseAuth.currentUser==null)
                    Log.d("debug","no current users")




            }
        }.addOnFailureListener{
            Log.d("debugggg",it.message.toString())
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoginManager.getInstance().registerCallback(callbackManager,object : FacebookCallback<LoginResult>
        {
            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {

            }

            override fun onSuccess(result: LoginResult) {
                handleFacebookaccessToken(result.accessToken)
            }
        })



        binding.fbBtn.setOnClickListener {
            LoginManager.getInstance().logOut()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"))

        }

        binding.signinBtn.setOnClickListener {
            findNavController().navigate(R.id.action_welcome_to_signIn)
        }


        googleSigninClient= welcomeModel.SetUpGoogleSignInClient()

        binding.ggBtn.setOnClickListener {

            googleSigninClient.signOut()
            openFragmentforResult()
            Log.d("debug","3")
        }
        signIn_error_listener()
        SignIn_success_listener()
        firebaseAuth.addAuthStateListener(firebaseAuth_listener)
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
                    Log.d("debug","6")
                } catch (e: ApiException) {
                    Log.d("SignIn", e.toString())
                }
            } else
                Log.d("Signin", task.exception.toString())


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
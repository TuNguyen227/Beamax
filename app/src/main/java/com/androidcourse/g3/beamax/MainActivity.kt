package com.androidcourse.g3.beamax

import android.app.Activity
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.pm.Signature
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Base64
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.androidcourse.g3.beamax.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences:SharedPreferences
    private var isAppinstallfirsttime=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Beamax)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val pref=getSharedPreferences("com.androidcourse.g3.beamax.R.xml.prefs", MODE_PRIVATE)
        val onInstallThefirstTime=pref.getBoolean("isFirstInstall",true)
        if(!onInstallThefirstTime)
        {
            var navhostFra=supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            var navInflater=navhostFra.navController.navInflater
            var graph=navInflater.inflate(R.navigation.nav_graph)
            graph.setStartDestination(R.id.welcome)
            navhostFra.navController.graph=graph
        }
        else
            getSharedPreferences("com.androidcourse.g3.beamax.R.xml.prefs", MODE_PRIVATE).edit().putBoolean("isFirstInstall",false).apply()



    }


}
package com.androidcourse.g3.beamax.application

import android.app.Application
import com.androidcourse.g3.beamax.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MainApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(onboardingAdapterModule, welcomeModelModule, homeViewModelModule,
                firebaseRepositoryModule, profileViewModelModule, signInViewModelModule,
                signUpViewModelModule, restaurantViewModelModule, bookingViewModelModule,
                menuViewModelModule, orderViewModelModule)
        }
    }

}
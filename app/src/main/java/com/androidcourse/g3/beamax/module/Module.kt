package com.androidcourse.g3.beamax.module


import com.androidcour.onboardingAdapter
import com.androidcourse.g3.beamax.ViewModel.*
import com.androidcourse.g3.beamax.base.BaseViewModel
import com.androidcourse.g3.beamax.repository.FirebaseRepository
import com.androidcourse.g3.beamax.screens.onboarding1
import com.androidcourse.g3.beamax.screens.onboarding2
import com.androidcourse.g3.beamax.screens.onboarding3
import com.androidcourse.group3.beamax.ViewModel.SignInViewModel
import com.androidcourse.group3.beamax.ViewModel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val onboardingAdapterModule= module {
    factory {params->
        onboardingAdapter(params.get(),params.get(),params.get())
    }
}

val welcomeModelModule= module {
    factory {
        WelcomeModel(get(),get(),get())
    }
}

val firebaseRepositoryModule= module {
    single {
        FirebaseRepository()
    }
}

val homeViewModelModule= module {
    viewModel{
        HomeViewModel(get(),get())
    }
}
val profileViewModelModule= module {
    viewModel{
        ProfileViewModel(get(),get())
    }
}
val restaurantViewModelModule= module {
    viewModel{
        RestaurantViewModel(get(),get())
    }
}
val signInViewModelModule= module {
    viewModel{
        SignInViewModel(get(),get())
    }
}
val signUpViewModelModule= module {
    viewModel{
        SignUpViewModel(get(),get())
    }
}
val bookingViewModelModule= module {
    viewModel{
        BookingViewModel(get(),get())
    }
}
val menuViewModelModule= module {
    viewModel {
        MenuViewModel(get(),get())
    }
}
val orderViewModelModule= module {
    viewModel {
        OrderViewModel(get(),get())
    }
}
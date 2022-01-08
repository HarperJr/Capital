package com.harper.capital.auth

import com.harper.capital.auth.signin.SignInFragment
import com.harper.capital.auth.signin.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule
    get() = module {

        scope<SignInFragment> {
            viewModel { SignInViewModel(get()) }
        }
    }
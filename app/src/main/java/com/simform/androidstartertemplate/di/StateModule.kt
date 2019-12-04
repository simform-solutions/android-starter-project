package com.simform.androidstartertemplate.di

import com.simform.androidstartertemplate.state.LoginState
import com.simform.androidstartertemplate.view.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val stateModule = module {
    //TODO: ADD Every
    factory {
        LoginState()
    }
}
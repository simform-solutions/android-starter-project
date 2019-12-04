package com.simform.androidstartertemplate.view.login

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.simform.androidstartertemplate.api.request.LoginRequest
import com.simform.androidstartertemplate.base.BaseState
import com.simform.androidstartertemplate.base.BaseViewModel
import com.simform.androidstartertemplate.extension.hasInternet
import com.simform.androidstartertemplate.state.LoginApiState
import com.simform.androidstartertemplate.state.LoginState
import com.simform.androidstartertemplate.state.LoginValidationState
import kotlinx.coroutines.launch

/**
 * Login View Model
 * @property loginState LoginState
 * @constructor
 */
class LoginViewModel(val state: LoginState) : BaseViewModel() {
    override fun provideState(): BaseState = state

    /**
     * on Login click
     */
    fun onLoginClick() {
        if (!context.hasInternet()) {
            state.validationState.value =
                LoginValidationState.NoInternet
            return
        }

        if (!isFormValid()) {
            return
        }

        state.loginApiState.value = LoginApiState.Loading
        val request = LoginRequest(
            email = state.emailLiveData.value.toString(),
            password = state.passwordLiveData.value.toString()
        )

        viewModelScope.launch {
            runCatching {
                apiServiceWithHeader.userLogin(
                    request
                )
            }.onSuccess {
                state.loginApiState.postValue(LoginApiState.Success(it))
            }.onFailure {
                state.loginApiState.postValue(
                    LoginApiState.Failure(it)
                )
            }
        }
    }

    private fun isFormValid(): Boolean = if (state.emailLiveData.value.isNullOrEmpty()) {
        state.validationState.value =
            LoginValidationState.EmptyEmail
        false
    } else if (!Patterns.EMAIL_ADDRESS.matcher(state.emailLiveData.value.toString()).matches()) {
        state.validationState.value =
            LoginValidationState.InvalidEmail
        false
    } else if (state.passwordLiveData.value.isNullOrEmpty()) {
        state.validationState.value =
            LoginValidationState.EmptyPassword
        false
    } else {
        true
    }

    /**
     * On Forgot TextView click
     */
    fun onForgotClick(){
        state.loginApiState.value = LoginApiState.ForgotClick
    }

    /**
     * On SignUp TextView click
     */
    fun onSignUpClick(){
        state.loginApiState.value = LoginApiState.SignUpClick
    }
}
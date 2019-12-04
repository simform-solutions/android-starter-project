package com.simform.androidstartertemplate.state


import androidx.lifecycle.MutableLiveData
import com.simform.androidstartertemplate.api.response.LoginResponse
import com.simform.androidstartertemplate.base.BaseState

/**
 * Login API state class
 * @property emailLiveData MutableLiveData<String>
 * @property passwordLiveData MutableLiveData<String>
 * @property validationState MutableLiveData<LoginValidationState>
 * @property loginApiState MutableLiveData<LoginApiState>
 */
class LoginState : BaseState {
    val emailLiveData = MutableLiveData<String>()
    val passwordLiveData = MutableLiveData<String>()
    val validationState = MutableLiveData<LoginValidationState>()
    val loginApiState = MutableLiveData<LoginApiState>()
}

/**
 * Login Validation state
 */
sealed class LoginValidationState {
    /**
     * InValid Email state
     */
    object InvalidEmail : LoginValidationState()

    /**
     * Empty Email state
     */
    object EmptyEmail : LoginValidationState()

    /**
     * Empty Password state
     */
    object EmptyPassword : LoginValidationState()

    /**
     * No Internet state
     */
    object NoInternet : LoginValidationState()
}

/**
 * Login API state
 */
sealed class LoginApiState {
    /**
     * Login loading API state
     */
    object Loading : LoginApiState()

    /**
     * Login success state
     * @property data LoginResponse
     * @constructor
     */
    data class Success(val data: LoginResponse) : LoginApiState()

    /**
     * Login failure state
     * @property throwable Throwable
     * @constructor
     */
    data class Failure(val throwable: Throwable) : LoginApiState()

    /**
     * Forgot TextView Click
     */
    object ForgotClick : LoginApiState()

    /**
     * Sign Up TextView Click
     */
    object SignUpClick : LoginApiState()
}
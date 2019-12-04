package com.simform.androidstartertemplate.view.login

import androidx.lifecycle.observe
import com.simform.androidstartertemplate.R
import com.simform.androidstartertemplate.api.response.LoginResponse
import com.simform.androidstartertemplate.base.BaseActivity
import com.simform.androidstartertemplate.databinding.LoginActivityBinding
import com.simform.androidstartertemplate.extension.disable
import com.simform.androidstartertemplate.extension.enable
import com.simform.androidstartertemplate.state.LoginApiState
import com.simform.androidstartertemplate.state.LoginState
import com.simform.androidstartertemplate.state.LoginValidationState
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject

class LoginActivity :
    BaseActivity<LoginActivityBinding, LoginState, LoginViewModel>(R.layout.activity_login) {

    override val viewModel: LoginViewModel by inject()

    override fun observeViewState(state: LoginState) {
        // Validation state goes here
        state.validationState.observe(this) {
            when (it) {
                is LoginValidationState.InvalidEmail -> {
                    toast(getString(R.string.error_msg_email_valid))
                }
                is LoginValidationState.EmptyEmail -> {
                    toast(getString(R.string.error_msg_email))
                }
                is LoginValidationState.EmptyPassword -> {
                    toast(getString(R.string.error_msg_password_login))
                }
            }
        }

        // API state like loading, failure and success. Live data will notify here after api request start
        state.loginApiState.observe(this) {
            when (it) {
                is LoginApiState.Loading -> {
                    // Display progress like progressbar or progress dialog
                    startStopAnimation(FLAG_START)
                }
                is LoginApiState.Failure -> {
                    // Hide progress first
                    startStopAnimation(FLAG_STOP)

                    toast(it.throwable.message.toString())
                }
                is LoginApiState.Success -> {
                    // Hide progress first
                    startStopAnimation(FLAG_STOP)

                    // Parse success login response
                    onLoginSuccess(it.data)
                }
                is LoginApiState.SignUpClick ->{
                    // Add code to redirect on Sign Up activity or fragment
                    toast("SignUp clicked")
                }
                is LoginApiState.ForgotClick ->{
                    // Add code to redirect on Forgot Password activity or fragment
                    toast("Forgot clicked")
                }
            }
        }
    }

    private fun onLoginSuccess(data: LoginResponse) {
        // User Login success response
    }

    val FLAG_START = 1
    val FLAG_STOP = 2
    /**
     * As per the flag we can enable and disable animation and view
     * @param flag Int
     */
    private fun startStopAnimation(flag: Int) {
        when (flag) {
            FLAG_START -> {
                buttonLogin.disable()
                buttonLogin.startAnimation()
            }
            FLAG_STOP -> {
                buttonLogin.enable()
                buttonLogin.revertAnimation()
            }
        }
    }


}
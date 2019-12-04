package com.simform.androidstartertemplate.base

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.simform.androidstartertemplate.BR
import com.simform.androidstartertemplate.R
import io.github.inflationx.viewpump.ViewPumpContextWrapper

/**
 * This is base activity of all activities
 */
abstract class BaseActivity<BINDING : ViewDataBinding, STATE : BaseState, VIEWMODEL : BaseViewModel>(@LayoutRes val layoutId: Int) :
    AppCompatActivity() {
    protected abstract val viewModel: VIEWMODEL
    protected val state: STATE by lazy {
        viewModel.provideState() as STATE
    }
    protected lateinit var binding: BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        initializeDataBinding()
        initializeViews()
        observeViewState(state)
    }

    open fun initializeViews() {}

    private fun initializeDataBinding() {
        DataBindingUtil.setContentView<BINDING>(this, layoutId)
            .apply {
                binding = this
                lifecycleOwner = this@BaseActivity
                // TODO: In all layout this variable name must be same
                setVariable(BR.viewModel, viewModel)
                setVariable(BR.state, state)
                executePendingBindings()
            }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    abstract fun observeViewState(state: STATE)

    fun toast(message: String, duration: Int = Toast.LENGTH_LONG) {
        /*val toast = Toast.makeText(this, message, duration)
        toast.view.setBackgroundResource(R.drawable.drawable_blue_rounded)
        toast.view.findViewById<TextView>(android.R.id.message)
            .setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        toast.view.findViewById<TextView>(android.R.id.message)
            .setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite))
        toast.show()*/
        Toast.makeText(this,message,duration).show()
    }

    fun toast(@StringRes message: Int) {
        toast(getString(message))
    }
}
package com.simform.androidstartertemplate.base

import android.content.Context
import androidx.lifecycle.ViewModel
import com.simform.androidstartertemplate.api.ApiService
import com.simform.androidstartertemplate.di.WITH_HEADER
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.GlobalContext
import org.koin.core.inject
import org.koin.core.qualifier.named

/**
 * This class is used as base view model extends all view models
 */
abstract class BaseViewModel : ViewModel(), KoinComponent {
    protected val apiServiceWithHeader: ApiService by inject(named(WITH_HEADER))
    protected val context: Context
        get() = GlobalContext.get().koin.rootScope.androidContext()
    abstract fun provideState(): BaseState
}
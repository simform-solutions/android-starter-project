package com.simform.androidstartertemplate

import android.app.Application
import com.simform.androidstartertemplate.di.viewModelModule
import com.simform.androidstartertemplate.di.appModule
import com.simform.androidstartertemplate.di.networkModule
import com.simform.androidstartertemplate.di.stateModule
import com.simform.androidstartertemplate.di.RETROFIT
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.ScopeID
import timber.log.Timber

/**
 * This is Application class
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoinModule()
        // initCallyGrapy()
        initTimber()
    }

    private fun initKoinModule() {
        startKoin {
            androidContext(applicationContext)
            modules(listOf(appModule, networkModule, stateModule, viewModelModule))
        }
        getKoin().createScope(ScopeID(), named(RETROFIT))
    }

    private fun initCallyGrapy() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            //TODO: your default font goes here
                            .setDefaultFontPath("fonts/gilroy_medium.otf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
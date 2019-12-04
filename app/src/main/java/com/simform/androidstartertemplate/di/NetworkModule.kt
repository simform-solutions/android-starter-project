package com.simform.androidstartertemplate.di

import com.google.gson.GsonBuilder
import com.simform.androidstartertemplate.BuildConfig
import com.simform.androidstartertemplate.api.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Default koin Network module
 */

const val WITH_HEADER: String = "WITH_HEADER"
const val WITH_OUT_AUTH_TOKEN_OK: String = "WITH_OUT_AUTH_TOKEN_OK"
const val HEADERS: String = "HEADERS"
const val HTTP_LOGGING_INTERCEPTOR: String = "HTTP_LOGGING_INTERCEPTOR"
const val RETROFIT: String = "RETROFIT"

val networkModule = module {
    single {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single<Interceptor>(named(HTTP_LOGGING_INTERCEPTOR)) {
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Timber.i(message)
        }).run {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    factory(named(HEADERS)) {
        Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
            //TODO: Add you auth token here if require
            /*val logPref: UserPreference by inject()
            logPref.data.data?.accessToken?.let { authToken ->
                request.header(AUTHORIZATION, "Bearer $authToken")
            }
            request.addHeader(DEVICE_ID, androidContext().getDeviceId())*/

            request.method(original.method(), original.body())
            chain.proceed(request.build())
        }
    }

    scope(named(RETROFIT)) {
        scoped(named(HEADERS)) {
            get<Interceptor>(named(HEADERS))
        }
    }

    // OkHttp Client Config
    single(named(WITH_OUT_AUTH_TOKEN_OK)) {
        OkHttpClient.Builder().apply {
            readTimeout(1, TimeUnit.MINUTES)
            connectTimeout(2, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
            addInterceptor(get(named(HTTP_LOGGING_INTERCEPTOR)))
            addInterceptor(get(named(HEADERS)))
        }.build()
    }

    // Retrofit Service
    single(named(WITH_HEADER)) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(get(named(WITH_OUT_AUTH_TOKEN_OK)))
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
            .create(ApiService::class.java)
    }
}
package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DI

import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.APIService
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.RetrofitService
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.BuildConfig
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.Util.API_BASE_URL
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.Util.API_CONNECT_TIMEOUT
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.Util.API_READ_TIMEOUT
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.Util.API_WRITE_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object APIModule {
    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(API_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(API_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(API_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .readTimeout(API_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(API_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(API_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

@InstallIn(SingletonComponent::class)
@Module
object AccessAPIServiceModule {
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    @Provides
    fun provideAPIService(service: RetrofitService): APIService {
        return APIService(service)
    }
}
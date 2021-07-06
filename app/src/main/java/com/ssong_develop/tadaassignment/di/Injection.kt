package com.ssong_develop.tadaassignment.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.ssong_develop.tadaassignment.api.RideEstimationService
import com.ssong_develop.tadaassignment.api.RideStatusService
import com.ssong_develop.tadaassignment.api.repository.RideEstimationRepository
import com.ssong_develop.tadaassignment.api.repository.RideStatusRepository
import com.ssong_develop.tadaassignment.local.SharedPref
import com.ssong_develop.tadaassignment.ui.factory.MainViewModelFactory
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Injection(private val application: Application) {

    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    private fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
            .build()

    private fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()

    private fun provideRideEstimationService() =
        provideRetrofit().create(RideEstimationService::class.java)

    private fun provideRideStatusService() =
        provideRetrofit().create(RideStatusService::class.java)

    private fun provideRideEstimationRepository() = RideEstimationRepository(
        provideRideEstimationService()
    )

    private fun provideRideStatusRepository() = RideStatusRepository(
        provideRideStatusService()
    )

    private fun provideSharedPref() = SharedPref(application)

    fun provideMainViewModelFactory(): ViewModelProvider.Factory =
        MainViewModelFactory(
            provideRideEstimationRepository(),
            provideRideStatusRepository(),
            provideSharedPref()
        )

    companion object {
        private const val BaseUrl = "https://us-central1-homework-client-4e5cb.cloudfunctions.net/"
    }
}
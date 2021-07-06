package com.ssong_develop.tadaassignment.di

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ssong_develop.tadaassignment.api.RideEstimationService
import com.ssong_develop.tadaassignment.api.RideStatusService
import com.ssong_develop.tadaassignment.api.repository.RideEstimationRepository
import com.ssong_develop.tadaassignment.api.repository.RideStatusRepository
import com.ssong_develop.tadaassignment.local.SharedPref
import com.ssong_develop.tadaassignment.ui.factory.MainViewModelFactory
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retry Code는 한번 해보고 안되면 그때 CallAdapterFactory 패턴으로 변경해서 해보는걸로 해봅시다.
 */
class Injection(private val application: Application) {

    private fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request()
                try {
                    var response = chain.proceed(request)
                    if (response.code == 408) {
                        kotlin.runCatching {
                            response.body?.close()
                            chain.proceed(request)
                        }.onSuccess {
                            response = it
                        }.onFailure {
                            Log.e("error", "timeOut Error")
                        }
                    }
                    response
                } catch (e: Throwable) {
                    throw e
                }
            })
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .protocols(listOf(Protocol.HTTP_1_1))
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
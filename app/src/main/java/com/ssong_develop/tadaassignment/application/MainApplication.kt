package com.ssong_develop.tadaassignment.application

import android.app.Application
import com.ssong_develop.tadaassignment.di.Injection

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeSingleton()
    }

    private fun initializeSingleton() {
        injection = Injection(this)
    }

    companion object {
        lateinit var injection: Injection
    }
}
package com.kmj.mapledictionary

import androidx.annotation.CallSuper
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : MultiDexApplication() {
    val baseUrl: String
        get() = "https://api.maplestory.net/"

    val baseUrl1: String
        get() = "https://maplestory.io/api/"

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MyApplication
            private set

    }
}
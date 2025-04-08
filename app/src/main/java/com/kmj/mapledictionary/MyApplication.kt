package com.kmj.mapledictionary

import androidx.annotation.CallSuper
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : MultiDexApplication() {
    val baseUrl: String
        get() = "https://api.maplestory.net/"

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
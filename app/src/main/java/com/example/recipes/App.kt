package com.example.recipes

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.HiltAndroidApp

@Suppress("DEPRECATION")
@HiltAndroidApp
class App() : Application(){

    override fun onCreate() {
        super.onCreate()

        // SDK Initialize for facebook login.
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }

}
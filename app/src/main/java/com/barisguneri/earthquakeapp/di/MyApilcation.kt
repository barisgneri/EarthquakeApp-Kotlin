package com.barisguneri.earthquakeapp.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val osmConfig = Configuration.getInstance()
        osmConfig.userAgentValue = BuildConfig.LIBRARY_PACKAGE_NAME
        osmConfig.load(this, getSharedPreferences("osm_prefs", MODE_PRIVATE))
    }
}
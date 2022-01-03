package com.chullian.terminustest

import android.app.Application
import coil.ImageLoader
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by binMammootty on 02/01/2022.
 */
@HiltAndroidApp
class App : Application() {
    val imageLoader by lazy {
        ImageLoader.Builder(applicationContext)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }

    }
    companion object {
        var instance: App? = null
    }

}
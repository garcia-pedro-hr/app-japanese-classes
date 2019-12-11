package com.phgarcia.msjc

import android.app.Application
import android.content.Context
import timber.log.Timber

class MsjcApplication : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Timber.plant(Timber.DebugTree())
    }
}
package com.phgarcia.msjc

import android.app.Application
import timber.log.Timber

class MsjcApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
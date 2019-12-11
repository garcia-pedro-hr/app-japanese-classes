package com.phgarcia.msjc.utils

import android.content.res.AssetManager
import com.phgarcia.msjc.MsjcApplication
import timber.log.Timber
import java.lang.Exception

fun openAsset(assetName: String): String {
    return try {
        val assetManager: AssetManager = MsjcApplication.appContext.assets
        assetManager.open(assetName).bufferedReader().use { it.readText() }
    } catch (e: Exception) {
        Timber.e("Couldn't open JSON file: ${e.message}")
        ""
    }
}
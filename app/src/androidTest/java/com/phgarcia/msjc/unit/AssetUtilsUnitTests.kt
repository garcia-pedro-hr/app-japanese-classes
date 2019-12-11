package com.phgarcia.msjc.unit

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.phgarcia.msjc.utils.openAsset
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AssetUtilsUnitTests {

    @Test
    fun assetUtils_getJsonFile() =
        Assert.assertFalse(openAsset("modules.txt").isEmpty())

}
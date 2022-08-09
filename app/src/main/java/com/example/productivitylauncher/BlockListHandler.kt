package com.example.productivitylauncher

import com.example.productivitylauncher.models.AppBlockInfo

object BlockListHandler {
    val appBlockList: MutableList<AppBlockInfo>

    init {
        appBlockList = ArrayList()

        appBlockList.add(
                AppBlockInfo(
                    "com.android.calculator2",
                    25000
                )
        )
    }
}
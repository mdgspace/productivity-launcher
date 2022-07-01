package com.example.productivitylauncher.models

import android.graphics.drawable.Drawable
import com.example.productivitylauncher.AppTimer


data class AppInfo(val label: CharSequence?, val packageName: CharSequence?, val icon: Drawable?, val timer: AppTimer?, val uid: Int?)

package com.kiren.mob21firebase

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application() {
    companion object {
      init {
         System.loadLibrary("mob21firebase2")
      }
    }
}
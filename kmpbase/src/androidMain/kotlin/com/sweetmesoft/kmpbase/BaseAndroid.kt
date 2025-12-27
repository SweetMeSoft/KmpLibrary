package com.sweetmesoft.kmpbase

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class BaseAndroid {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var currentActivity: ComponentActivity

        fun initSweetMeSoft(context: ComponentActivity) {
            currentActivity = context
            currentActivity.enableEdgeToEdge()
        }

        fun getContext(): Context {
            return currentActivity
        }
    }
}
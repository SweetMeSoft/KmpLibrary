package com.sweetmesoft.kmpbase

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.ComponentActivity

class BaseAndroid {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var currentActivity: ComponentActivity

        fun initSweetMeSoft(context: ComponentActivity) {
            currentActivity = context
        }

        fun getContext(): Context {
            return currentActivity
        }
    }
}
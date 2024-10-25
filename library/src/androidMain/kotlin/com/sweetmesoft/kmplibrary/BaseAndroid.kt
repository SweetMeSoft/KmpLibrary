package com.sweetmesoft.kmplibrary

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.ComponentActivity

class BaseAndroid {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var currentActivity: ComponentActivity

        fun init(context: ComponentActivity) {
            currentActivity = context;
        }

        fun getContext(): Context {
            return currentActivity
        }
    }
}
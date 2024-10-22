package com.sweetmesoft.kmplibrary

import android.annotation.SuppressLint
import android.content.Context

class BaseAndroid {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var currentContext: Context

        fun init(context: Context) {
            currentContext = context;
        }

        fun getContext(): Context {
            return currentContext;
        }
    }
}
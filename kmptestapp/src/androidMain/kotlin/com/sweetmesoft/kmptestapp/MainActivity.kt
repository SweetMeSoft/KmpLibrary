package com.sweetmesoft.kmptestapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sweetmesoft.kmpbase.BaseAndroid.Companion.initSweetMeSoft

class MainActivity : ComponentActivity() {
    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSweetMeSoft(this@MainActivity)

        setContent {
            App()
        }
    }

    companion object {
        private var instance: MainActivity? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun currentActivity(): ComponentActivity {
            return instance!!
        }
    }
}
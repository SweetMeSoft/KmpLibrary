package com.sweetmesoft.kmpcontrols.tools

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresPermission
import androidx.core.content.getSystemService
import com.sweetmesoft.kmpcontrols.tools.BaseAndroid.Companion.getContext

actual object Vibrator {
    @RequiresPermission("android.permission.VIBRATE")
    actual fun vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getContext().getSystemService<VibratorManager>()
            if (vibratorManager != null) {
                if (vibratorManager.defaultVibrator.hasVibrator()) {
                    val vibrationEffect =
                        VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
                    vibratorManager.defaultVibrator.vibrate(vibrationEffect)
                }
            }
        } else {
            val vibrator = getContext().getSystemService<Vibrator>()
            vibrator?.vibrate(10)
        }
    }
}
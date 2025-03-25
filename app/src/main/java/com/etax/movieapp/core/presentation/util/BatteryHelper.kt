package com.etax.movieapp.core.presentation.util

import android.content.Context
import android.os.BatteryManager

class BatteryHelper(private val context: Context) {
    fun isBatteryGood(): Boolean {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        return batteryLevel > 20
    }
}
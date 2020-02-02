package com.example.a22housexam2.Utility

import androidx.core.content.ContextCompat
import android.os.Build
import android.app.Activity
import com.example.a22housexam2.R


object Utils {
    enum class StatusBarColorType private constructor(val backgroundColorId: Int) {

        WHITE_STATUS_BAR(R.color.White),

        BLUE_STATUS_BAR(R.color.themeColor),
    }

    fun setStatusBarColor(activity: Activity, colorType: StatusBarColorType) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor =
                ContextCompat.getColor(activity, colorType.backgroundColorId)
        }
    }
}
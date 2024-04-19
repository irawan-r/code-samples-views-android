package com.amora.firebase.ui.util

import android.view.View
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

fun View.tryCrashButton() {
    this.setOnClickListener {
        throw RuntimeException("Try Crash!")
    }
}

fun FirebaseAnalytics.trackScreen(screenName: String, screenClass: Class<*>) {
    logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
        param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass.name)
    }
}

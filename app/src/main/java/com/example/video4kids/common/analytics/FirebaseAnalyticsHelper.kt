package com.example.video4kids.common.analytics

import android.content.Context
import android.os.Bundle
import com.example.video4kids.App
import com.google.firebase.analytics.FirebaseAnalytics

object FirebaseAnalyticsHelper {
    private lateinit var analytics : FirebaseAnalytics

    fun init(context: Context) {
        analytics = FirebaseAnalytics.getInstance(context)
    }

    fun setUserProperty(property: String) {
        analytics.setUserProperty(FirebaseAnalytics.UserProperty.SIGN_UP_METHOD, property)
    }

    fun logEvent(event: String, data: Map<String, String>) {
        val bundle = Bundle()
        for ((key, value) in data) {
            bundle.putString(key, value)
        }
        analytics.logEvent(event, bundle)
    }
}
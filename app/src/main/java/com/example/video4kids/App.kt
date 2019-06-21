package com.example.video4kids

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.example.video4kids.activities.MainActivity
import com.example.video4kids.common.analytics.FirebaseAnalyticsHelper
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.mcxiaoke.koi.KoiConfig
import io.fabric.sdk.android.Fabric

class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var activity: MainActivity
    }

    init {
        context = this

        KoiConfig.logEnabled = BuildConfig.DEBUG
        KoiConfig.logLevel = Log.VERBOSE
    }

    override fun onCreate() {
        super.onCreate()

        PreferenceHolder.setContext(this)
        Fabric.with(this, Crashlytics())
        FirebaseAnalyticsHelper.init(this)
    }
}
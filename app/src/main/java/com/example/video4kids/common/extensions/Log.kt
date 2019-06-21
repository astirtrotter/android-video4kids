package com.example.video4kids.common.extensions

import android.util.Log
import com.example.video4kids.App
import com.mcxiaoke.koi.log.*

fun log(message: String, logLevel: Int = Log.VERBOSE) {
    when (logLevel) {
        Log.VERBOSE ->
            App.context.logv(message)
        Log.INFO ->
            App.context.logi(message)
        Log.DEBUG ->
            App.context.logd(message)
        Log.ERROR ->
            App.context.loge(message)
        Log.WARN ->
            App.context.logw(message)
        else ->
            App.context.logf(message)
    }
}
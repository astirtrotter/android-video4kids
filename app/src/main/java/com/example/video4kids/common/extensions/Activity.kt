package com.example.video4kids.common.extensions

import android.app.Activity
import android.content.Intent
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.start

inline fun <reified T: Activity> Activity.gotoAnotherActivity() {
    val intent = IntentFor<T>(this)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    intent.start(this)
    finish()
}

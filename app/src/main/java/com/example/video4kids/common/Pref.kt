package com.example.video4kids.common

import com.marcinmoskala.kotlinpreferences.PreferenceHolder

object Pref : PreferenceHolder() {
    var isAdult: Boolean? by bindToPreferenceFieldNullable()
    var isEnglish: Boolean? by bindToPreferenceFieldNullable()
    var isPasscodeSet: Boolean? by bindToPreferenceFieldNullable()

    val isFirstLaunch: Boolean
        get() = isAdult == null || isEnglish == null
}
package com.example.video4kids.common

import com.marcinmoskala.kotlinpreferences.PreferenceHolder

object Pref : PreferenceHolder() {
    var isAll: Boolean? by bindToPreferenceFieldNullable()
    var isEnglish: Boolean? by bindToPreferenceFieldNullable()
    var isPasscodeSet: Boolean? by bindToPreferenceFieldNullable()
    var blockVideoIds: ArrayList<String> by bindToPreferenceField(ArrayList())
    var favVideoIds: ArrayList<String> by bindToPreferenceField(ArrayList())

    val isFirstLaunch: Boolean
        get() = isAll == null || isEnglish == null
}
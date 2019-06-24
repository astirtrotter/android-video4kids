package com.example.video4kids.common

import com.example.video4kids.common.backend.api.VideoItem
import com.google.gson.Gson
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.marcinmoskala.kotlinpreferences.gson.GsonSerializer

object Pref : PreferenceHolder() {
    init {
        serializer = GsonSerializer(Gson())
    }

    var isAll: Boolean? by bindToPreferenceFieldNullable()
    var isEnglish: Boolean? by bindToPreferenceFieldNullable()
    var isPasscodeSet: Boolean? by bindToPreferenceFieldNullable()
    var blockVideoIds: ArrayList<Int> by bindToPreferenceField(ArrayList())
    var favVideoItems: ArrayList<VideoItem> by bindToPreferenceField(ArrayList())

    val isFirstLaunch: Boolean
        get() = isAll == null || isEnglish == null
    val path: String
        get() = "2".takeIf { isEnglish!! } ?: "1"
}
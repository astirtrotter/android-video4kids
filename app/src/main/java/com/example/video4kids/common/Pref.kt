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
    var blockVideoIds: ArrayList<String> by bindToPreferenceField(ArrayList())
    var favVideoItems: ArrayList<VideoItem> by bindToPreferenceField(ArrayList())

    // exposed virtual properties

    val isFirstLaunch: Boolean
        get() = isAll == null || isEnglish == null
    val path: String
        get() = "2".takeIf { isEnglish!! } ?: "1"
    fun blockVideo(videoId: String): Boolean {
        if (blockVideoIds.contains(videoId)) return false
        blockVideoIds.add(videoId)
        return true
    }
    fun favoriteVideo(item: VideoItem): Boolean {
        if (item.isFav) return false
        favVideoItems.add(item)
        return true
    }
    fun unfavoriteVideo(item: VideoItem): Boolean {
        if (!item.isFav) return false
        favVideoItems.removeAll { it.video_id == item.video_id }
        return true
    }
}
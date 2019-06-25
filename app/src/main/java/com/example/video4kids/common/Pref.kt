package com.example.video4kids.common

import com.example.video4kids.common.backend.api.VideoItem
import com.google.gson.Gson
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.marcinmoskala.kotlinpreferences.gson.GsonSerializer

object Pref : PreferenceHolder() {
    init {
        serializer = GsonSerializer(Gson())
    }

    var isForAllAge: Boolean? by bindToPreferenceFieldNullable()
    var isEnglish: Boolean? by bindToPreferenceFieldNullable()
    var passcode: String? by bindToPreferenceFieldNullable()
    var blockVideoIds: List<String> by bindToPreferenceField(arrayListOf())
    var favVideoItems: List<VideoItem> by bindToPreferenceField(arrayListOf())

    // exposed virtual properties

    val isFirstLaunch: Boolean
        get() = isForAllAge == null || isEnglish == null
    val path: String
        get() = "2".takeIf { isEnglish!! } ?: "1"
    fun blockVideo(videoId: String): Boolean {
        if (blockVideoIds.contains(videoId)) return false
        blockVideoIds = ArrayList(blockVideoIds).apply { add(videoId) }
        return true
    }
    fun favoriteVideo(item: VideoItem): Boolean {
        if (item.isFav) return false
        favVideoItems = ArrayList(favVideoItems).apply { add(item) }
        return true
    }
    fun unfavoriteVideo(item: VideoItem): Boolean {
        if (!item.isFav) return false
        favVideoItems = ArrayList(favVideoItems.filter { it.video_id != item.video_id })
        return true
    }
}
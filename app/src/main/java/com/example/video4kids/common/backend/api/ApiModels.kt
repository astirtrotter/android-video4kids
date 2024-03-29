package com.example.video4kids.common.backend.api

import com.example.video4kids.common.Pref

data class Format(val id: Int?,
                  val video_id: Int?,
                  val itag: Int?,
                  val video_url: String?,
//                  val created_at: String?,
//                  val updated_at: String?,
                  val quality: String?)

data class Tag(val id: Int?,
               //val tag_group_id: Any?,
               val slug: String?,
               val name: String?,
               val suggest: Int?,
               val count: Int?)

data class Tagged(val id: Int?,
                  val taggable_id: Int?,
                  val taggable_type: String?,
                  val tag_name: String?,
                  val tag_slug: String?,
                  val tag: Tag?)

data class VideoItem(val id: Int?,
                     val video_id: String?,
                     val category_id: Int?,
                     val age_id: Int?,
                     val video_title: String?,
                     val video_description: String?,
                     val thumbnail_url: String,
                     val video_url: String,
//                     val publish_at: Any?,
//                     val created_at: String?,
//                     val updated_at: String?,
                     val direct_webm_url: String?,
                     val related_video_present: Boolean?,
                     val formats: List<Format>?,
                     val tagged: List<Tagged>?) {

    // virtual properties
    val isFav: Boolean
        get() = Pref.favVideoItems.any { it.video_id == video_id }
    val title: String
        get() = video_description.takeIf { Pref.isEnglish!! } ?: video_title!!
    val relatedVideoCount: Int
        get() = 0.takeIf { tagged?.isEmpty() ?: true } ?: tagged!![0].tag!!.count ?: 0
    val videoPath: String
        get() = video_url
    val videoCount: Int
        get() = relatedVideoCount
    val imagePath: String
        get() = thumbnail_url
    val tagSlug: String
        get() = "".takeIf { tagged?.isEmpty() ?: true } ?: tagged!![0].tag!!.slug!!
}
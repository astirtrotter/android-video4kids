package com.example.video4kids.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VideoList {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("video_id")
    @Expose
    var videoId: String? = null

    @SerializedName("category_id")
    @Expose
    var categoryId: Int? = null

    @SerializedName("age_id")
    @Expose
    var ageId: Int? = null

    @SerializedName("video_title")
    @Expose
    var videoTitle: String? = null

    @SerializedName("video_description")
    @Expose
    var videoDescription: String? = null

    @SerializedName("thumbnail_url")
    @Expose
    var thumbnailUrl: String? = null

    @SerializedName("video_url")
    @Expose
    var videoUrl: String? = null

    @SerializedName("publish_at")
    @Expose
    var publishAt: Any? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("direct_webm_url")
    @Expose
    var directWebmUrl: String? = null

    @SerializedName("related_video_present")
    @Expose
    var relatedVideoPresent: Boolean? = null

    @SerializedName("formats")
    @Expose
    var formats: List<Format>? = null

    @SerializedName("tagged")
    @Expose
    var tagged: List<Tagged>? = null
}


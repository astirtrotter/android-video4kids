package com.example.video4kids.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Format {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("video_id")
    @Expose
    var videoId: Int? = null

    @SerializedName("itag")
    @Expose
    var itag: Int? = null

    @SerializedName("video_url")
    @Expose
    var videoUrl: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("quality")
    @Expose
    var quality: String? = null

}
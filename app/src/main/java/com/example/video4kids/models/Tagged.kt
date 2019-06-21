package com.example.video4kids.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Tagged {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("taggable_id")
    @Expose
    var taggableId: Int? = null

    @SerializedName("taggable_type")
    @Expose
    var taggableType: String? = null

    @SerializedName("tag_name")
    @Expose
    var tagName: String? = null

    @SerializedName("tag_slug")
    @Expose
    var tagSlug: String? = null

    @SerializedName("tag")
    @Expose
    var tag: Tag? = null

}
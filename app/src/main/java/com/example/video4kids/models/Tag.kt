package com.example.video4kids.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Tag {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("tag_group_id")
    @Expose
    var tagGroupId: Any? = null

    @SerializedName("slug")
    @Expose
    var slug: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("suggest")
    @Expose
    var suggest: Int? = null

    @SerializedName("count")
    @Expose
    var count: Int? = null

}
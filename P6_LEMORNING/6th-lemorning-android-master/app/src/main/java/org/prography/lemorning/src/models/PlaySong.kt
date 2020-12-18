package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class PlaySong (
    @SerializedName("id") var no: Int,
    @SerializedName("name") var title: String?,
    @SerializedName("category_name") var category: String?,
    @SerializedName("image") var imgUrl: String?,
    @SerializedName("alarm") var musicUrl: String?,
    @SerializedName("tags") var tags: ArrayList<String?>
)
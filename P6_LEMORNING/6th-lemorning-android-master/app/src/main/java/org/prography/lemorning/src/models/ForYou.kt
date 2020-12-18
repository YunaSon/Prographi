package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class ForYou (
    @SerializedName("id") var no: Int,
    @SerializedName("image") var thumbnail : String?,
    @SerializedName("name") var title : String?,
    @SerializedName("category_name") var category : String?,
    @SerializedName("tags") var tags: ArrayList<String?>
)
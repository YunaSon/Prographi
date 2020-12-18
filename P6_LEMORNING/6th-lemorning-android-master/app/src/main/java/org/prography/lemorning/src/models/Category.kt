package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

class Category (
    @SerializedName("name") var name : String?,
    @SerializedName("thumbnail") var imgUrl : String?
)
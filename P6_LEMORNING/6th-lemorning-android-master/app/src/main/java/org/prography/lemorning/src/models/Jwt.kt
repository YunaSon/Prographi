package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class Jwt(
    @SerializedName("token") var token : String
)
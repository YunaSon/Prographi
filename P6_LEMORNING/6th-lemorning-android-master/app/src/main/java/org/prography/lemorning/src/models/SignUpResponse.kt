package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("status") var status : Int
)
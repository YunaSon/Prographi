package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class SignInParam(
    @SerializedName("username") var email : String?,
    @SerializedName("password") var password : String?
)

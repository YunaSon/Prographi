package org.prography.lemorning.utils.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T> (
    @SerializedName("code")
    val code: Int,
    @SerializedName("response")
    val response: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T
)
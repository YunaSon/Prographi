package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class Card (
    @SerializedName("bank") var bank : String?,
    @SerializedName("no") var no : String?
)
package com.iyam.mysuitmediatest.data.network.api.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Support(
    @SerializedName("text")
    val text: String?,
    @SerializedName("url")
    val url: String?
)

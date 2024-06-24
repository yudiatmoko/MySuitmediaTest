package com.iyam.mysuitmediatest.data.network.api.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Support(
    @SerializedName("text")
    val text: String?,
    @SerializedName("url")
    val url: String?
)
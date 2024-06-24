package com.iyam.mysuitmediatest.data.network.api.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UsersResponse(
    @SerializedName("data")
    val users: List<UserItemResponse>?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("support")
    val support: Support?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
)

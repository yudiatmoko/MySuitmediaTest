package com.iyam.mysuitmediatest.data.network.api.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.iyam.mysuitmediatest.model.User

@Keep
data class UserItemResponse(
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("last_name")
    val lastName: String?
)

fun UserItemResponse.toUser() = User(
    id = this.id ?: 0,
    firstName = this.firstName.orEmpty(),
    lastName = this.lastName.orEmpty(),
    email = this.email.orEmpty(),
    avatar = this.avatar.orEmpty()
)

fun Collection<UserItemResponse>.toUserList() = this.map {
    it.toUser()
}

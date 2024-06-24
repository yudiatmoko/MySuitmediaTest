package com.iyam.mysuitmediatest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

@Parcelize
data class User(
    val avatar: String?,
    val email: String?,
    val firstName: String?,
    val id: Int?,
    val lastName: String?
) : Parcelable

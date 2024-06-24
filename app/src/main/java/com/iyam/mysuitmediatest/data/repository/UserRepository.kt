package com.iyam.mysuitmediatest.data.repository

import com.iyam.mysuitmediatest.data.network.api.datasource.MySuitmediaAPIDataSource
import com.iyam.mysuitmediatest.data.network.api.model.toUserList
import com.iyam.mysuitmediatest.model.User


/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

interface UserRepository {
    suspend fun getUsers(): List<User>
}

class UserRepositoryImpl(
    private val dataSource: MySuitmediaAPIDataSource
): UserRepository {
    override suspend fun getUsers(): List<User> {
        return dataSource.getUsers().users?.toUserList() ?: emptyList()
    }
}
package com.iyam.mysuitmediatest.data.network.api.datasource

import com.iyam.mysuitmediatest.data.network.api.model.UsersResponse
import com.iyam.mysuitmediatest.data.network.api.service.MySuitmediaAPIService


/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

interface MySuitmediaAPIDataSource {
    suspend fun getUsers(): UsersResponse
}

class MySuitmediaAPIDataSourceImpl(
    private val service: MySuitmediaAPIService
) : MySuitmediaAPIDataSource{
    override suspend fun getUsers(): UsersResponse {
        return service.getUsers()
    }
}
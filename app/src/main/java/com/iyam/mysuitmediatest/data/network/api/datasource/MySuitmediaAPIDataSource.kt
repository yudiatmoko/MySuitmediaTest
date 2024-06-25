package com.iyam.mysuitmediatest.data.network.api.datasource

import com.iyam.mysuitmediatest.data.network.api.model.UsersResponse
import com.iyam.mysuitmediatest.data.network.api.service.MySuitmediaAPIService

/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

interface MySuitmediaAPIDataSource {
    suspend fun getUsers(parameters: HashMap<String, String>): UsersResponse
}

class MySuitmediaAPIDataSourceImpl(
    private val service: MySuitmediaAPIService
) : MySuitmediaAPIDataSource {
    override suspend fun getUsers(parameters: HashMap<String, String>): UsersResponse {
        return service.getUsers(parameters)
    }
}

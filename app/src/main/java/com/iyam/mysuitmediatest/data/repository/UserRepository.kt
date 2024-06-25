package com.iyam.mysuitmediatest.data.repository

import com.iyam.mysuitmediatest.data.network.api.datasource.MySuitmediaAPIDataSource
import com.iyam.mysuitmediatest.data.network.api.model.toUserList
import com.iyam.mysuitmediatest.model.User
import com.iyam.mysuitmediatest.utils.ResultWrapper
import com.iyam.mysuitmediatest.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

interface UserRepository {
    suspend fun getUsers(parameters: HashMap<String, String>): Flow<ResultWrapper<List<User>>>
}

class UserRepositoryImpl(
    private val dataSource: MySuitmediaAPIDataSource
) : UserRepository {
    override suspend fun getUsers(parameters: HashMap<String, String>): Flow<ResultWrapper<List<User>>> {
        return proceedFlow {
            dataSource.getUsers(parameters).users?.toUserList() ?: emptyList()
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }
    }
}

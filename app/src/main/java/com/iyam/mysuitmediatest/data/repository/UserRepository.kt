package com.iyam.mysuitmediatest.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.iyam.mysuitmediatest.data.network.api.datasource.MySuitmediaAPIDataSource
import com.iyam.mysuitmediatest.model.User
import com.iyam.mysuitmediatest.paging.UserPagingSource
import com.iyam.mysuitmediatest.utils.ResultWrapper
import com.iyam.mysuitmediatest.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

interface UserRepository {
    fun getAllUsers(): Flow<ResultWrapper<LiveData<PagingData<User>>>>
}

class UserRepositoryImpl(
    private val dataSource: MySuitmediaAPIDataSource
) : UserRepository {

    override fun getAllUsers(): Flow<ResultWrapper<LiveData<PagingData<User>>>> {
        return proceedFlow {
            Pager(
                config = PagingConfig(
                    pageSize = 2,
                    enablePlaceholders = false,
                    initialLoadSize = 2
                ),
                pagingSourceFactory = {
                    UserPagingSource(dataSource)
                },
                initialKey = 1
            ).liveData
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }
}

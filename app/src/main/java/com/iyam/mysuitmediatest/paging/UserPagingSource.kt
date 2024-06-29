package com.iyam.mysuitmediatest.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.iyam.mysuitmediatest.data.network.api.datasource.MySuitmediaAPIDataSource
import com.iyam.mysuitmediatest.data.network.api.model.toUserList
import com.iyam.mysuitmediatest.model.User
import kotlinx.coroutines.delay

/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

class UserPagingSource(
    private val dataSource: MySuitmediaAPIDataSource
) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val position = params.key ?: 1
            val response = dataSource.getUsers(position, 10)
            val totalPage = dataSource.getUsers(position, 10).totalPages?.plus(1)
            LoadResult.Page(
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == totalPage) {
                    delay(2000)
                    null
                } else {
                    position + 1
                },
                data = response.users?.toUserList() ?: emptyList()
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

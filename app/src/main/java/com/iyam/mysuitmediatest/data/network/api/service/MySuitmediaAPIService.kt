package com.iyam.mysuitmediatest.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.iyam.mysuitmediatest.BuildConfig
import com.iyam.mysuitmediatest.data.network.api.model.UsersResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

interface MySuitmediaAPIService {
    @GET("users?page=1&per_page=10")
    suspend fun getUsers(): UsersResponse

    companion object {
        @JvmStatic
        operator fun invoke(
            chucker: ChuckerInterceptor
        ): MySuitmediaAPIService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chucker)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .client(okHttpClient)
                .build()
            return retrofit.create(
                MySuitmediaAPIService::class.java
            )
        }
    }
}

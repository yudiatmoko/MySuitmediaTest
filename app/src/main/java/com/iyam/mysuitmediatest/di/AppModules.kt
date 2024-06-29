package com.iyam.mysuitmediatest.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.iyam.mysuitmediatest.data.local.datastore.UserPreferenceDataSource
import com.iyam.mysuitmediatest.data.local.datastore.UserPreferenceDataSourceImpl
import com.iyam.mysuitmediatest.data.local.datastore.appDataStore
import com.iyam.mysuitmediatest.data.network.api.datasource.MySuitmediaAPIDataSource
import com.iyam.mysuitmediatest.data.network.api.datasource.MySuitmediaAPIDataSourceImpl
import com.iyam.mysuitmediatest.data.network.api.service.MySuitmediaAPIService
import com.iyam.mysuitmediatest.data.repository.UserRepository
import com.iyam.mysuitmediatest.data.repository.UserRepositoryImpl
import com.iyam.mysuitmediatest.paging.UserPagingSource
import com.iyam.mysuitmediatest.presentation.firstscreen.MainViewModel
import com.iyam.mysuitmediatest.presentation.secondscreen.SecondScreenViewModel
import com.iyam.mysuitmediatest.presentation.thirdscreen.ThirdScreenViewModel
import com.iyam.mysuitmediatest.utils.PreferenceDataStoreHelper
import com.iyam.mysuitmediatest.utils.PreferenceDataStoreHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { MySuitmediaAPIService.invoke(get()) }
    }

    private val localModule = module {
        single { androidContext().appDataStore }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
    }

    private val dataSourceModule = module {
        single<MySuitmediaAPIDataSource> { MySuitmediaAPIDataSourceImpl(get()) }
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
    }

    private val repositoryModule = module {
        single<UserRepository> { UserRepositoryImpl(get()) }
        single { UserPagingSource(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::ThirdScreenViewModel)
        viewModelOf(::SecondScreenViewModel)
        viewModelOf(::MainViewModel)
    }

    val modules: List<Module> = listOf(
        networkModule,
        localModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule
    )
}

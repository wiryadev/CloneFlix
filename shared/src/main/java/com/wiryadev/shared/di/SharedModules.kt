package com.wiryadev.shared.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.wiryadev.core.base.BaseModules
import com.wiryadev.shared.data.local.datastore.UserPreferenceDataSource
import com.wiryadev.shared.data.local.datastore.UserPreferenceDataSourceImpl
import com.wiryadev.shared.data.local.datastore.UserPreferenceFactory
import com.wiryadev.shared.data.remote.NetworkClient
import com.wiryadev.shared.data.remote.datasource.SharedFeatureRemoteDataSource
import com.wiryadev.shared.data.remote.datasource.SharedFeatureRemoteDataSourceImpl
import com.wiryadev.shared.data.remote.service.SharedFeatureService
import com.wiryadev.shared.data.repository.SharedApiRepository
import com.wiryadev.shared.data.repository.SharedApiRepositoryImpl
import com.wiryadev.shared.data.repository.UserPreferenceRepository
import com.wiryadev.shared.data.repository.UserPreferenceRepositoryImpl
import com.wiryadev.shared.domain.GetUserTokenUseCase
import com.wiryadev.shared.domain.SaveAuthDataUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object SharedModules : BaseModules {

    override fun getModules(): List<Module> =
        listOf(remote, local, dataSource, repository, sharedUseCase, common)

    private val remote = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { NetworkClient(get(), get()) }
        single<SharedFeatureService> { get<NetworkClient>().create() }
    }

    private val local = module {
        single { UserPreferenceFactory(androidContext()).create() }
    }

    private val dataSource = module {
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get(), get()) }
        single<SharedFeatureRemoteDataSource> { SharedFeatureRemoteDataSourceImpl(get()) }
    }

    private val repository = module {
        single<UserPreferenceRepository> { UserPreferenceRepositoryImpl(get()) }
        single<SharedApiRepository> { SharedApiRepositoryImpl(get()) }
    }

    private val sharedUseCase = module {
        single { GetUserTokenUseCase(get(), Dispatchers.IO) }
        single { SaveAuthDataUseCase(get(), Dispatchers.IO) }
    }

    private val common = module {
        single { Gson() }
    }

}
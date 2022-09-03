package com.wiryadev.splashscreen.di

import com.wiryadev.core.base.FeatureModules
import com.wiryadev.shared.data.remote.NetworkClient
import com.wiryadev.splashscreen.data.network.datasource.SplashscreenDatasource
import com.wiryadev.splashscreen.data.network.datasource.SplashscreenDatasourceImpl
import com.wiryadev.splashscreen.data.network.service.SplashscreenService
import com.wiryadev.splashscreen.data.repository.SplashscreenRepository
import com.wiryadev.splashscreen.data.repository.SplashscreenRepositoryImpl
import com.wiryadev.splashscreen.domain.SyncUserUseCase
import com.wiryadev.splashscreen.presentation.SplashscreenViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object SplashscreenModule : FeatureModules {

    override val repositories: Module = module {
        single<SplashscreenRepository> { SplashscreenRepositoryImpl(get()) }
    }

    override val viewModels: Module = module {
        viewModelOf(::SplashscreenViewModel)
    }

    override val dataSources: Module = module {
        single<SplashscreenDatasource> { SplashscreenDatasourceImpl(get()) }
    }

    override val useCases: Module = module {
        single { SyncUserUseCase(get(), get(), Dispatchers.IO) }
    }

    override val network: Module = module {
        single<SplashscreenService> { get<NetworkClient>().create() }
    }

    override fun getModules(): List<Module> {
        return listOf(repositories, viewModels, dataSources, useCases, network)
    }
}
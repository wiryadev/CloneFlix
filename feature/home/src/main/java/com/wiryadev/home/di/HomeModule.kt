package com.wiryadev.home.di

import com.wiryadev.core.base.FeatureModules
import com.wiryadev.home.data.network.datasource.HomeDataSource
import com.wiryadev.home.data.network.datasource.HomeDataSourceImpl
import com.wiryadev.home.data.network.service.HomeService
import com.wiryadev.home.data.repository.HomeRepository
import com.wiryadev.home.data.repository.HomeRepositoryImpl
import com.wiryadev.home.domain.GetHomeFeedUseCase
import com.wiryadev.home.domain.GetUserWatchlistUseCase
import com.wiryadev.home.presentation.ui.home.HomeViewModel
import com.wiryadev.shared.data.remote.NetworkClient
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object HomeModule : FeatureModules {

    override fun getModules(): List<Module> = listOf(
        repositories, viewModels, dataSources, useCases, network
    )

    override val repositories: Module = module {
        single<HomeRepository> { HomeRepositoryImpl(get()) }
    }

    override val viewModels: Module = module {
        viewModelOf(::HomeViewModel)
    }

    override val dataSources: Module = module {
        single<HomeDataSource> { HomeDataSourceImpl(get()) }
    }

    override val useCases: Module = module {
        single { GetHomeFeedUseCase(get(), Dispatchers.IO) }
        single { GetUserWatchlistUseCase(get(), Dispatchers.IO) }
    }

    override val network: Module = module {
        single<HomeService> { get<NetworkClient>().create() }
    }

}
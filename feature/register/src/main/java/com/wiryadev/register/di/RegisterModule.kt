package com.wiryadev.register.di

import com.wiryadev.core.base.FeatureModules
import com.wiryadev.register.data.network.datasource.RegisterDataSource
import com.wiryadev.register.data.network.datasource.RegisterDataSourceImpl
import com.wiryadev.register.data.network.service.RegisterService
import com.wiryadev.register.data.repository.RegisterRepository
import com.wiryadev.register.data.repository.RegisterRepositoryImpl
import com.wiryadev.register.domain.CheckRegisterFieldUseCase
import com.wiryadev.register.domain.RegisterUserUseCase
import com.wiryadev.register.presentation.RegisterViewModel
import com.wiryadev.shared.data.remote.NetworkClient
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object RegisterModule : FeatureModules {

    override fun getModules(): List<Module> = listOf(
        repositories,
        viewModels,
        dataSources,
        useCases,
        network
    )

    override val repositories: Module = module {
        singleOf(::RegisterRepositoryImpl) bind RegisterRepository::class
    }

    override val viewModels: Module = module {
        viewModelOf(::RegisterViewModel)
    }

    override val dataSources: Module = module {
        singleOf(::RegisterDataSourceImpl) bind RegisterDataSource::class
    }

    override val useCases: Module = module {
        single { CheckRegisterFieldUseCase(Dispatchers.IO) }
        single { RegisterUserUseCase(get(), get(), get(), Dispatchers.IO) }
    }

    override val network: Module = module {
        single<RegisterService> { get<NetworkClient>().create() }
    }


}
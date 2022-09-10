package com.wiryadev.login.di

import com.wiryadev.core.base.FeatureModules
import com.wiryadev.login.data.network.datasource.LoginDataSource
import com.wiryadev.login.data.network.datasource.LoginDataSourceImpl
import com.wiryadev.login.data.network.service.LoginService
import com.wiryadev.login.data.repository.LoginRepository
import com.wiryadev.login.data.repository.LoginRepositoryImpl
import com.wiryadev.login.domain.CheckLoginFieldUseCase
import com.wiryadev.login.domain.LoginUserUseCase
import com.wiryadev.login.presentation.LoginViewModel
import com.wiryadev.shared.data.remote.NetworkClient
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object LoginModule : FeatureModules {

    override fun getModules(): List<Module> = listOf(
        repositories, dataSources, viewModels, useCases, network
    )

    override val repositories: Module = module {
        single<LoginRepository> { LoginRepositoryImpl(get()) }
    }

    override val viewModels: Module = module {
        viewModelOf(::LoginViewModel)
    }

    override val dataSources: Module = module {
        single<LoginDataSource> { LoginDataSourceImpl(get()) }
    }

    override val useCases: Module = module {
        single { CheckLoginFieldUseCase(Dispatchers.IO) }
        single { LoginUserUseCase(get(), get(), get(), Dispatchers.IO) }
    }

    override val network: Module = module {
        single<LoginService> { get<NetworkClient>().create() }
    }
}
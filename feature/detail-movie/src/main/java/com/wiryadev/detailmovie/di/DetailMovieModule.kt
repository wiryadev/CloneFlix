package com.wiryadev.detailmovie.di

import com.wiryadev.core.base.FeatureModules
import com.wiryadev.detailmovie.data.network.datasource.DetailMovieDataSource
import com.wiryadev.detailmovie.data.network.datasource.DetailMovieDataSourceImpl
import com.wiryadev.detailmovie.data.network.service.DetailMovieService
import com.wiryadev.detailmovie.data.repository.DetailMovieRepository
import com.wiryadev.detailmovie.data.repository.DetailMovieRepositoryImpl
import com.wiryadev.detailmovie.domain.GetDetailMovieUseCase
import com.wiryadev.detailmovie.presentation.detailmovie.DetailMovieViewModel
import com.wiryadev.detailmovie.presentation.movieinfo.MovieInfoViewModel
import com.wiryadev.shared.data.remote.NetworkClient
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object DetailMovieModule : FeatureModules {

    override fun getModules(): List<Module> = listOf(
        network, dataSources, repositories, useCases, viewModels,
    )

    override val repositories: Module = module {
        single<DetailMovieRepository> { DetailMovieRepositoryImpl(get()) }
    }

    override val viewModels: Module = module {
        viewModelOf(::DetailMovieViewModel)
        viewModelOf(::MovieInfoViewModel)
    }

    override val dataSources: Module = module {
        single<DetailMovieDataSource> { DetailMovieDataSourceImpl(get()) }
    }

    override val useCases: Module = module {
        single { GetDetailMovieUseCase(get(), Dispatchers.IO) }
    }

    override val network: Module = module {
        single<DetailMovieService> { get<NetworkClient>().create() }
    }


}
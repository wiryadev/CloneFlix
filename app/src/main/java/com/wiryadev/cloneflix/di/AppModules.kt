package com.wiryadev.cloneflix.di

import com.wiryadev.cloneflix.router.ActivityRouterImpl
import com.wiryadev.core.base.BaseModules
import com.wiryadev.shared.router.ActivityRouter
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules : BaseModules {

    override fun getModules(): List<Module> = listOf(router)

    val router = module {
        single<ActivityRouter> { ActivityRouterImpl() }
    }
}
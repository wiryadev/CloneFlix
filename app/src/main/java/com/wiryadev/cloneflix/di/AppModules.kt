package com.wiryadev.cloneflix.di

import com.wiryadev.cloneflix.router.ActivityRouterImpl
import com.wiryadev.cloneflix.router.BottomSheetRouterImpl
import com.wiryadev.cloneflix.router.FragmentRouterImpl
import com.wiryadev.core.base.BaseModules
import com.wiryadev.shared.router.ActivityRouter
import com.wiryadev.shared.router.BottomSheetRouter
import com.wiryadev.shared.router.FragmentRouter
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules : BaseModules {

    override fun getModules(): List<Module> = listOf(router)

    private val router = module {
        single<ActivityRouter> { ActivityRouterImpl() }
        single<BottomSheetRouter> { BottomSheetRouterImpl() }
        single<FragmentRouter> { FragmentRouterImpl() }
    }

}
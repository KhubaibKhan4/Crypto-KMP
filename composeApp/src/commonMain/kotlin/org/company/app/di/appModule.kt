package org.company.app.di

import org.company.app.domain.repository.Repository
import org.company.app.presentation.viewmodel.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single {
        Repository()
    }
    singleOf(::MainViewModel)
}
package com.harper.capital.repository

import com.harper.capital.spec.repository.AssetRepository
import org.koin.dsl.module

val repositoryModule
    get() = module {

        factory<AssetRepository> { AssetRepositoryImpl(get(), get()) }
    }
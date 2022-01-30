package com.harper.capital.repository

import org.koin.dsl.module

val repositoryModule
    get() = module {

        factory<AssetRepository> { AssetRepositoryImpl(get(), get()) }

        factory<TransactionRepository> { TransactionRepositoryImpl(get(), get()) }
    }
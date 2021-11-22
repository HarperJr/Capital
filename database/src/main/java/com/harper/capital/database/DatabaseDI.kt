package com.harper.capital.database

import androidx.room.Room
import org.koin.dsl.module

val databaseModule
    get() = module {

        single {
            Room.databaseBuilder(get(), Database::class.java, BuildConfig.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        factory { get<Database>().assetDao() }
    }
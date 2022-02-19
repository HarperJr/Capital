package com.harper.capital.category

import com.harper.capital.category.domain.AddCategoryUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule
    get() = module {
        factory { AddCategoryUseCase(get()) }

        viewModel { (params: CategoryManageParams) ->
            CategoryManageViewModel(params, get(), get())
        }
    }

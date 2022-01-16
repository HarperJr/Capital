package com.harper.capital.category

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule
    get() = module {

        scope<CategoryManageFragment> {
            viewModel { (params: CategoryManageFragment.Params) -> CategoryManageViewModel(params, get()) }
        }
    }

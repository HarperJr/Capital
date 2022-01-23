package com.harper.capital.category

import com.harper.capital.category.domain.AddCategoryUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule
    get() = module {

        scope<CategoryManageFragment> {
            scoped { AddCategoryUseCase(get()) }
            viewModel { (params: CategoryManageFragment.Params) ->
                CategoryManageViewModel(
                    params,
                    get(),
                    get()
                )
            }
        }
    }

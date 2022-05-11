package com.harper.capital.liability

import com.harper.capital.liability.domain.AddLiabilityUseCase
import com.harper.capital.liability.domain.FetchContactsUseCase
import com.harper.capital.settings.domain.GetSettingsUseCase
import com.harper.capital.storage.ContactsProvider
import com.harper.capital.transaction.manage.domain.FetchAccountUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val liabilityModule
    get() = module {

        factory { AddLiabilityUseCase(get()) }

        factory { FetchContactsUseCase(ContactsProvider(get())) }

        factory { GetSettingsUseCase(get()) }

        factory { FetchAccountUseCase(get()) }

        viewModel { (params: LiabilityManageParams) ->
            LiabilityManageViewModel(params, get(), get(), get(), get(), get())
        }
    }

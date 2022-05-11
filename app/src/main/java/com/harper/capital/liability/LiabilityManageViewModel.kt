package com.harper.capital.liability

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.liability.domain.AddLiabilityUseCase
import com.harper.capital.liability.domain.FetchContactsUseCase
import com.harper.capital.liability.model.LiabilityManageBottomSheet
import com.harper.capital.liability.model.LiabilityManageBottomSheetState
import com.harper.capital.liability.model.LiabilityManageEvent
import com.harper.capital.liability.model.LiabilityManagePage
import com.harper.capital.liability.model.LiabilityManageState
import com.harper.capital.liability.model.LiabilityManageType
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.settings.domain.GetSettingsUseCase
import com.harper.capital.transaction.manage.domain.FetchAccountUseCase
import com.harper.core.ext.lazyAsync
import com.harper.core.ext.orElse
import com.harper.core.ext.tryCast
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.launch

class LiabilityManageViewModel(
    private val params: LiabilityManageParams,
    private val fetchAccountUseCase: FetchAccountUseCase,
    private val addLiabilityUseCase: AddLiabilityUseCase,
    private val fetchContactsUseCase: FetchContactsUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val router: GlobalRouter
) : ComponentViewModel<LiabilityManageState, LiabilityManageEvent>(
    initialState = LiabilityManageState(selectedPage = params.type.ordinal)
) {
    private val lazySettings = lazyAsync { getSettingsUseCase() }

    override fun onFirstComposition() {
        super.onFirstComposition()
        launch {
            val pages = createPages(params.accountId?.let { fetchAccountUseCase(it) })
            update { it.copy(pages = pages) }
        }
    }

    override fun onEvent(event: LiabilityManageEvent) {
        when (event) {
            is LiabilityManageEvent.CurrencySelect -> onCurrencySelect(event)
            is LiabilityManageEvent.IconSelect -> onIconSelect(event)
            is LiabilityManageEvent.NameChange -> onNameChange(event)
            is LiabilityManageEvent.AmountChange -> onAmountChange(event)
            is LiabilityManageEvent.TabSelect -> onTabSelect(event)
            is LiabilityManageEvent.CurrencySelectClick -> onCurrencyClick()
            is LiabilityManageEvent.IconSelectClick -> onIconSelectClick()
            is LiabilityManageEvent.BackClick -> router.back()
            is LiabilityManageEvent.Apply -> onApply()
            is LiabilityManageEvent.ContactSelectClick -> onContactSelectClick()
            is LiabilityManageEvent.ContactSelect -> onContactSelect(event)
            is LiabilityManageEvent.PhoneChange -> onPhoneChange(event)
        }
    }

    private suspend fun createPages(account: Account?): List<LiabilityManagePage> {
        return if (account == null) {
            val settings = lazySettings.await()
            return listOf(
                LiabilityManagePage(type = LiabilityManageType.LIABILITY, currency = settings.currency, icon = AccountIcon.PRODUCTS),
                LiabilityManagePage(type = LiabilityManageType.INCOME, currency = settings.currency, icon = AccountIcon.BANK),
                LiabilityManagePage(type = LiabilityManageType.DEBT, currency = settings.currency, icon = AccountIcon.DEBT)
            )
        } else {
            with(account) {
                listOf(
                    LiabilityManagePage(
                        type = params.type,
                        name = name,
                        amount = balance,
                        currency = currency,
                        icon = icon,
                        metadata = metadata
                    )
                )
            }
        }
    }

    private fun onApply() {
        launch {
            val liability = with(state.value) {
                val currentPage = pages[selectedPage]
                val type = when (currentPage.type) {
                    LiabilityManageType.LIABILITY -> AccountType.LIABILITY
                    LiabilityManageType.DEBT -> AccountType.LIABILITY
                    LiabilityManageType.INCOME -> AccountType.INCOME
                }
                Account(
                    id = 0L,
                    name = currentPage.name,
                    type = type,
                    balance = currentPage.amount,
                    currency = currentPage.currency,
                    icon = currentPage.icon,
                    color = AccountColor.LIABILITY,
                    metadata = currentPage.metadata
                )
            }
            addLiabilityUseCase(liability)
            router.back()
        }
    }

    private fun onNameChange(event: LiabilityManageEvent.NameChange) {
        update {
            val pages = updateSelectedPage(it) { page ->
                page.copy(name = event.name)
            }
            it.copy(pages = pages)
        }
    }

    private fun onAmountChange(event: LiabilityManageEvent.AmountChange) {
        update {
            val pages = updateSelectedPage(it) { page ->
                page.copy(amount = event.amount)
            }
            it.copy(pages = pages)
        }
    }

    private fun onIconSelect(event: LiabilityManageEvent.IconSelect) {
        update {
            val pages = updateSelectedPage(it) { page ->
                page.copy(icon = AccountIcon.valueOf(event.iconName))
            }
            it.copy(pages = pages, bottomSheetState = it.bottomSheetState.copy(isExpended = false))
        }
    }

    private fun onCurrencySelect(event: LiabilityManageEvent.CurrencySelect) {
        update {
            val pages = updateSelectedPage(it) { page ->
                page.copy(currency = event.currency)
            }
            it.copy(pages = pages, bottomSheetState = it.bottomSheetState.copy(isExpended = false))
        }
    }

    private fun onPhoneChange(event: LiabilityManageEvent.PhoneChange) {
        update {
            val pages = updateSelectedPage(it) { page ->
                val metadata = page.metadata.tryCast<AccountMetadata.Debt>()?.copy(phone = event.phone)
                    .orElse(AccountMetadata.Debt(null, event.phone))
                page.copy(metadata = metadata)
            }
            it.copy(pages = pages)
        }
    }

    private fun onContactSelect(event: LiabilityManageEvent.ContactSelect) {
        update {
            with(event.contact) {
                val pages = updateSelectedPage(it) { page ->
                    val metadata = AccountMetadata.Debt(avatar, phone)
                    page.copy(name = name, metadata = metadata)
                }
                it.copy(pages = pages, bottomSheetState = it.bottomSheetState.copy(isExpended = false))
            }
        }
    }

    private fun onContactSelectClick() {
        launch {
            val contacts = fetchContactsUseCase()
            update {
                it.copy(
                    bottomSheetState = LiabilityManageBottomSheetState(
                        bottomSheet = LiabilityManageBottomSheet.Contacts(contacts, null)
                    )
                )
            }
        }
    }

    private fun updateSelectedPage(
        state: LiabilityManageState,
        update: (LiabilityManagePage) -> LiabilityManagePage
    ): List<LiabilityManagePage> {
        val pages = state.pages.mapIndexed { index, page ->
            if (index == state.selectedPage) {
                update.invoke(page)
            } else {
                page
            }
        }
        return pages
    }

    private fun onTabSelect(event: LiabilityManageEvent.TabSelect) {
        update {
            it.copy(selectedPage = event.tabIndex)
        }
    }

    private fun onCurrencyClick() {
        update {
            val currency = it.pages[it.selectedPage].currency
            it.copy(
                bottomSheetState = LiabilityManageBottomSheetState(
                    bottomSheet = LiabilityManageBottomSheet.Currencies(
                        Currency.values().toList(),
                        currency
                    )
                )
            )
        }
    }

    private fun onIconSelectClick() {
        update {
            val icon = it.pages[it.selectedPage].icon
            it.copy(
                bottomSheetState = LiabilityManageBottomSheetState(
                    bottomSheet = LiabilityManageBottomSheet.Icons(icon)
                )
            )
        }
    }
}

package com.harper.capital.category

import com.harper.capital.category.domain.AddCategoryUseCase
import com.harper.capital.category.model.CategoryManageBottomSheet
import com.harper.capital.category.model.CategoryManageBottomSheetState
import com.harper.capital.category.model.CategoryManageEvent
import com.harper.capital.category.model.CategoryManagePage
import com.harper.capital.category.model.CategoryManageState
import com.harper.capital.category.model.CategoryManageType
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.navigation.GlobalRouter
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.launch

class CategoryManageViewModel(
    params: CategoryManageParams,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val router: GlobalRouter
) : ComponentViewModel<CategoryManageState, CategoryManageEvent>(
    initialState = CategoryManageState(selectedPage = params.type.ordinal)
) {

    override fun onEvent(event: CategoryManageEvent) {
        when (event) {
            is CategoryManageEvent.CurrencySelect -> onCurrencySelect(event)
            is CategoryManageEvent.IconSelect -> onIconSelect(event)
            is CategoryManageEvent.NameChange -> onNameChange(event)
            is CategoryManageEvent.AmountChange -> onAmountChange(event)
            is CategoryManageEvent.TabSelect -> onTabSelect(event)
            CategoryManageEvent.CurrencySelectClick -> onCurrencyClick()
            CategoryManageEvent.IconSelectClick -> onIconSelectClick()
            CategoryManageEvent.BackClick -> router.back()
            CategoryManageEvent.Apply -> onApply()
        }
    }

    private fun onApply() {
        launch {
            val category = with(state.value) {
                val currentPage = pages[selectedPage]
                val type = when (currentPage.type) {
                    CategoryManageType.LIABILITY -> AccountType.LIABILITY
                    CategoryManageType.INCOME -> AccountType.INCOME
                }
                Account(
                    id = 0L,
                    name = currentPage.name,
                    type = type,
                    balance = currentPage.amount,
                    currency = currentPage.currency,
                    icon = currentPage.icon,
                    color = AccountColor.CATEGORY,
                    metadata = null
                )
            }
            addCategoryUseCase(category)
            router.back()
        }
    }

    private fun onNameChange(event: CategoryManageEvent.NameChange) {
        update {
            val pages = updateSelectedPage(it) { page ->
                page.copy(name = event.name)
            }
            it.copy(pages = pages)
        }
    }

    private fun onAmountChange(event: CategoryManageEvent.AmountChange) {
        update {
            val pages = updateSelectedPage(it) { page ->
                page.copy(amount = event.amount)
            }
            it.copy(pages = pages)
        }
    }

    private fun onIconSelect(event: CategoryManageEvent.IconSelect) {
        update {
            val pages = updateSelectedPage(it) { page ->
                page.copy(icon = AccountIcon.valueOf(event.iconName))
            }
            it.copy(pages = pages, bottomSheetState = it.bottomSheetState.copy(isExpended = false))
        }
    }

    private fun onCurrencySelect(event: CategoryManageEvent.CurrencySelect) {
        update {
            val pages = updateSelectedPage(it) { page ->
                page.copy(currency = event.currency)
            }
            it.copy(pages = pages, bottomSheetState = it.bottomSheetState.copy(isExpended = false))
        }
    }

    private fun updateSelectedPage(
        state: CategoryManageState,
        update: (CategoryManagePage) -> CategoryManagePage
    ): List<CategoryManagePage> {
        val pages = state.pages.mapIndexed { index, page ->
            if (index == state.selectedPage) {
                update.invoke(page)
            } else {
                page
            }
        }
        return pages
    }

    private fun onTabSelect(event: CategoryManageEvent.TabSelect) {
        update {
            it.copy(selectedPage = event.tabIndex)
        }
    }

    private fun onCurrencyClick() {
        update {
            val currency = it.pages[it.selectedPage].currency
            it.copy(
                bottomSheetState = CategoryManageBottomSheetState(
                    bottomSheet = CategoryManageBottomSheet.Currencies(
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
                bottomSheetState = CategoryManageBottomSheetState(
                    bottomSheet = CategoryManageBottomSheet.Icons(icon)
                )
            )
        }
    }
}

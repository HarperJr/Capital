package com.harper.capital.category

import com.harper.capital.category.model.CategoryManageBottomSheet
import com.harper.capital.category.model.CategoryManageBottomSheetState
import com.harper.capital.category.model.CategoryManageEvent
import com.harper.capital.category.model.CategoryManageState
import com.harper.capital.domain.model.CategoryIcon
import com.harper.capital.domain.model.Currency
import com.harper.capital.navigation.GlobalRouter
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class CategoryManageViewModel(
    params: CategoryManageFragment.Params,
    private val router: GlobalRouter
) : ComponentViewModel<CategoryManageState>(
    defaultState = CategoryManageState(selectedPage = params.type.ordinal)
), EventObserver<CategoryManageEvent> {

    override fun onEvent(event: CategoryManageEvent) {
        when (event) {
            is CategoryManageEvent.CurrencySelect -> onCurrencySelect(event)
            is CategoryManageEvent.IconSelect -> onIconSelect(event)
            is CategoryManageEvent.TabSelect -> onTabSelect(event)
            CategoryManageEvent.CurrencySelectClick -> onCurrencyClick()
            CategoryManageEvent.IconSelectClick -> onIconSelectClick()
            CategoryManageEvent.BlackClick -> router.back()
        }
    }

    private fun onIconSelect(event: CategoryManageEvent.IconSelect) {
        mutateState {
            val pages = it.pages.mapIndexed { index, page ->
                if (index == it.selectedPage) {
                    page.copy(icon = CategoryIcon.valueOf(event.iconName))
                } else {
                    page
                }
            }
            it.copy(pages = pages, bottomSheetState = it.bottomSheetState.copy(isExpended = false))
        }
    }

    private fun onCurrencySelect(event: CategoryManageEvent.CurrencySelect) {
        mutateState {
            val pages = it.pages.mapIndexed { index, page ->
                if (index == it.selectedPage) {
                    page.copy(currency = event.currency)
                } else {
                    page
                }
            }
            it.copy(pages = pages, bottomSheetState = it.bottomSheetState.copy(isExpended = false))
        }
    }

    private fun onTabSelect(event: CategoryManageEvent.TabSelect) {
        mutateState {
            it.copy(selectedPage = event.tabIndex)
        }
    }

    private fun onCurrencyClick() {
        mutateState {
            val currency = it.pages[it.selectedPage].currency
            it.copy(
                bottomSheetState = CategoryManageBottomSheetState(
                    bottomSheet = CategoryManageBottomSheet.Currencies(Currency.values().toList(), currency)
                )
            )
        }
    }

    private fun onIconSelectClick() {
        mutateState {
            val icon = it.pages[it.selectedPage].icon
            it.copy(
                bottomSheetState = CategoryManageBottomSheetState(
                    bottomSheet = CategoryManageBottomSheet.Icons(icon)
                )
            )
        }
    }
}

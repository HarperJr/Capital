package com.harper.capital.category.model

import com.harper.capital.domain.model.Currency

sealed class CategoryManageEvent {

    object CurrencySelectClick : CategoryManageEvent()

    class CurrencySelect(val currency: Currency) : CategoryManageEvent()
}
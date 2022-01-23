package com.harper.capital.category.model

import com.harper.capital.domain.model.Currency

sealed class CategoryManageEvent {

    object CurrencySelectClick : CategoryManageEvent()

    object IconSelectClick : CategoryManageEvent()

    object BackClick : CategoryManageEvent()

    object Apply : CategoryManageEvent()

    class CurrencySelect(val currency: Currency) : CategoryManageEvent()

    class IconSelect(val iconName: String) : CategoryManageEvent()

    class NameChange(val name: String) : CategoryManageEvent()

    class AmountChange(val amount: Double) : CategoryManageEvent()

    class TabSelect(val tabIndex: Int) : CategoryManageEvent()
}

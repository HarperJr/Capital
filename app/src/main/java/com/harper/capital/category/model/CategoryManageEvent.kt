package com.harper.capital.category.model

import com.harper.capital.domain.model.Currency

sealed class CategoryManageEvent {

    object CurrencySelectClick : CategoryManageEvent()

    object IconSelectClick : CategoryManageEvent()

    object BlackClick : CategoryManageEvent()

    class CurrencySelect(val currency: Currency) : CategoryManageEvent()

    class IconSelect(val iconName: String) : CategoryManageEvent()

    class TabSelect(val tabIndex: Int) : CategoryManageEvent()
}

package com.harper.capital.overview.asset.ui.model

import com.harper.capital.spec.domain.AssetColor
import com.harper.capital.spec.domain.AssetIcon
import com.harper.capital.spec.domain.Currency

sealed class AssetAddEvent {

    class ColorSelect(val color: AssetColor) : AssetAddEvent()

    class CurrencySelect(val currency: Currency) : AssetAddEvent()

    class NameChange(val name: String) : AssetAddEvent()

    class AmountChange(val amount: Double) : AssetAddEvent()

    class IconSelect(val icon: AssetIcon) : AssetAddEvent()

    object Apply : AssetAddEvent()

    object CurrencySelectClick : AssetAddEvent()

    object IconSelectClick : AssetAddEvent()
}

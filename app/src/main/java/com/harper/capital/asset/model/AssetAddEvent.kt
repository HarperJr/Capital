package com.harper.capital.asset.model

import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetType
import com.harper.capital.domain.model.Currency

sealed class AssetAddEvent {

    class ColorSelect(val color: AssetColor) : AssetAddEvent()

    class CurrencySelect(val currency: Currency) : AssetAddEvent()

    class NameChange(val name: String) : AssetAddEvent()

    class AmountChange(val amount: Double) : AssetAddEvent()

    class IconSelect(val iconName: String) : AssetAddEvent()

    class IncludeAssetCheckedChange(val isChecked: Boolean) : AssetAddEvent()

    class AssetTypeSelect(val assetTypeName: String) : AssetAddEvent()

    object Apply : AssetAddEvent()

    object CurrencySelectClick : AssetAddEvent()

    object IconSelectClick : AssetAddEvent()

    object AssetTypeSelectClick : AssetAddEvent()

    object BackClick : AssetAddEvent()
}

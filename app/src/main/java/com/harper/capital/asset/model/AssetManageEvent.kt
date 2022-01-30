package com.harper.capital.asset.model

import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.Currency

sealed class AssetManageEvent {

    class ColorSelect(val color: AssetColor) : AssetManageEvent()

    class CurrencySelect(val currency: Currency) : AssetManageEvent()

    class NameChange(val name: String) : AssetManageEvent()

    class AmountChange(val amount: Double) : AssetManageEvent()

    class IconSelect(val iconName: String) : AssetManageEvent()

    class IncludeAssetCheckedChange(val isChecked: Boolean) : AssetManageEvent()

    class AssetTypeSelect(val assetTypeName: String) : AssetManageEvent()

    class ActivateAssetCheckedChange(val isActive: Boolean) : AssetManageEvent()

    object Apply : AssetManageEvent()

    object CurrencySelectClick : AssetManageEvent()

    object IconSelectClick : AssetManageEvent()

    object AssetTypeSelectClick : AssetManageEvent()

    object BackClick : AssetManageEvent()
}

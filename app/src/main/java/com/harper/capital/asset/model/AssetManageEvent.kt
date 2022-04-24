package com.harper.capital.asset.model

import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.Currency

sealed class AssetManageEvent {

    class ColorSelect(val color: AccountColor) : AssetManageEvent()

    class CurrencySelect(val currency: Currency) : AssetManageEvent()

    class NameChange(val name: String) : AssetManageEvent()

    class AmountChange(val amount: Double) : AssetManageEvent()

    class IconSelect(val iconName: String) : AssetManageEvent()

    class IncludeAssetCheckedChange(val isChecked: Boolean) : AssetManageEvent()

    class AssetTypeSelect(val assetTypeName: String) : AssetManageEvent()

    class ActivateAssetCheckedChange(val isChecked: Boolean) : AssetManageEvent()

    class MetadataValueChange(val value: Double) : AssetManageEvent()

    object Apply : AssetManageEvent()

    object CurrencySelectClick : AssetManageEvent()

    object IconSelectClick : AssetManageEvent()

    object AssetTypeSelectClick : AssetManageEvent()

    object BackClick : AssetManageEvent()
}

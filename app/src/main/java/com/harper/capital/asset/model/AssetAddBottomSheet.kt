package com.harper.capital.asset.model

import androidx.compose.runtime.Composable
import com.harper.capital.bottomsheet.IconsBottomSheetData
import com.harper.capital.bottomsheet.SelectorBottomSheetData
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetType
import com.harper.capital.domain.model.Currency
import com.harper.capital.ext.getImageVector
import com.harper.capital.ext.getText

sealed class AssetAddBottomSheet {

    class Icons(private val selectedIcon: AssetIcon) : AssetAddBottomSheet() {
        val data: IconsBottomSheetData
            @Composable
            get() = IconsBottomSheetData(
                icons = AssetIcon.values().map {
                    IconsBottomSheetData.Icon(it.name, it.getImageVector())
                },
                selectedIcon = selectedIcon.name
            )
    }

    class AssetTypes(private val selectedAssetType: AssetType) : AssetAddBottomSheet() {
        val data: SelectorBottomSheetData
            @Composable
            get() = SelectorBottomSheetData(
                values = AssetType.values().map {
                    SelectorBottomSheetData.Value(it.name, it.getText())
                },
                selectedValue = selectedAssetType.name
            )
    }

    class Currencies(val currencies: List<Currency>, val selectedCurrency: Currency) : AssetAddBottomSheet()
}

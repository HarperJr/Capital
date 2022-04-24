package com.harper.capital.asset.model

import androidx.compose.runtime.Composable
import com.harper.capital.bottomsheet.IconsBottomSheetData
import com.harper.capital.bottomsheet.SelectorBottomSheetData
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.Currency
import com.harper.capital.ext.getImageVector
import com.harper.capital.ext.resolveText

sealed class AssetManageBottomSheet {

    class Icons(private val selectedIcon: AccountIcon) : AssetManageBottomSheet() {
        val data: IconsBottomSheetData
            @Composable
            get() = IconsBottomSheetData(
                icons = AccountIcon.values().map {
                    IconsBottomSheetData.Icon(it.name, it.getImageVector())
                },
                selectedIcon = selectedIcon.name
            )
    }

    class MetadataTypes(private val selectedAccountType: AssetMetadataType) : AssetManageBottomSheet() {
        val data: SelectorBottomSheetData
            @Composable
            get() = SelectorBottomSheetData(
                values = AssetMetadataType.values().map {
                    SelectorBottomSheetData.Value(it.name, it.resolveText())
                },
                selectedValue = selectedAccountType.name
            )
    }

    class Currencies(val currencies: List<Currency>, val selectedCurrency: Currency) :
        AssetManageBottomSheet()
}

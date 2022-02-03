package com.harper.capital.asset.model

import androidx.annotation.StringRes
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetType
import com.harper.capital.domain.model.Currency

data class AssetManageState(
    val mode: AssetManageMode,
    val name: String = "",
    val amount: Double = 0.0,
    val currency: Currency = Currency.RUB,
    val colors: List<AssetColor> = AssetColor.values().toList(),
    val color: AssetColor = AssetColor.TINKOFF,
    val icon: AssetIcon = AssetIcon.TINKOFF,
    val assetType: AssetType = AssetType.DEBET,
    val isIncluded: Boolean = true,
    val isArchived: Boolean = true,
    val bottomSheetState: AssetManageBottomSheetState = AssetManageBottomSheetState(isExpended = false),
    @StringRes val errorMessage: Int? = null
)

data class AssetManageBottomSheetState(
    val bottomSheet: AssetManageBottomSheet? = null,
    val isExpended: Boolean = true
)

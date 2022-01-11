package com.harper.capital.asset.model

import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.Currency

data class AssetManageState(
    val name: String = "",
    val amount: Double = 0.0,
    val currency: Currency = Currency.RUB,
    val colors: List<AssetColor> = AssetColor.values().toList(),
    val color: AssetColor = AssetColor.DARK_TINKOFF,
    val icon: AssetIcon = AssetIcon.TINKOFF,
    val metadata: AssetMetadata = AssetMetadata.Default,
    val bottomSheetState: AssetManageBottomSheetState = AssetManageBottomSheetState(isExpended = false)
)

data class AssetManageBottomSheetState(
    val bottomSheet: AssetManageBottomSheet? = null,
    val isExpended: Boolean = true
)

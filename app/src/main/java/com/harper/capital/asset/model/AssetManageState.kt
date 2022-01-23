package com.harper.capital.asset.model

import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.Currency

private const val DEFAULT_NAME = "New Card"

data class AssetManageState(
    val name: String = DEFAULT_NAME,
    val amount: Double = 0.0,
    val currency: Currency = Currency.RUB,
    val colors: List<AssetColor> = AssetColor.values().toList(),
    val color: AssetColor = AssetColor.TINKOFF,
    val icon: AssetIcon = AssetIcon.TINKOFF,
    val metadata: AssetMetadata = AssetMetadata.Debet,
    val bottomSheetState: AssetManageBottomSheetState = AssetManageBottomSheetState(isExpended = false)
)

data class AssetManageBottomSheetState(
    val bottomSheet: AssetManageBottomSheet? = null,
    val isExpended: Boolean = true
)

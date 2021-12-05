package com.harper.capital.overview.asset.ui.model

import com.harper.capital.spec.domain.AssetColor
import com.harper.capital.spec.domain.AssetIcon
import com.harper.capital.spec.domain.Currency
import com.harper.core.ui.model.Event

data class AssetAddState(
    val name: String = "",
    val amount: Double = 0.0,
    val currency: Currency = Currency.RUB,
    val colors: List<AssetColor> = AssetColor.values().toList(),
    val color: AssetColor = AssetColor.DARK_TINKOFF,
    val icon: AssetIcon = AssetIcon.TINKOFF,
    val isBottomSheetExpendedEvent: Event<Boolean> = Event(false),
    val bottomSheet: AssetAddEventBottomSheet? = null,
)

package com.harper.capital.asset.model

import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.Currency

data class AssetManageState(
    val mode: AssetManageMode,
    val isLoading: Boolean,
    val name: String = "",
    val balance: Double = 0.0,
    val currency: Currency = Currency.RUB,
    val colors: List<AccountColor> = AccountColor.assetColors(),
    val color: AccountColor = AccountColor.TINKOFF,
    val icon: AccountIcon = AccountIcon.TINKOFF,
    val metadata: AccountMetadata? = null,
    val isIncluded: Boolean = true,
    val isArchived: Boolean = false,
    val bottomSheetState: AssetManageBottomSheetState = AssetManageBottomSheetState(isExpended = false)
)

data class AssetManageBottomSheetState(
    val bottomSheet: AssetManageBottomSheet? = null,
    val isExpended: Boolean = true
)

package com.harper.capital.asset.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.harper.capital.domain.model.AssetColor

class AssetManageStateProvider : PreviewParameterProvider<AssetManageState> {
    override val values: Sequence<AssetManageState>
        get() = sequenceOf(
            AssetManageState(
                name = "",
                amount = 12434.44,
                colors = AssetColor.values().toList(),
                color = AssetColor.DARK_TINKOFF
            )
        )
}

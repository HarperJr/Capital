package com.harper.capital.asset.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.harper.capital.domain.model.AssetColor

class AssetAddStateProvider : PreviewParameterProvider<AssetAddState> {
    override val values: Sequence<AssetAddState>
        get() = sequenceOf(
            AssetAddState(
                name = "",
                amount = 12434.44,
                colors = AssetColor.values().toList(),
                color = AssetColor.DARK_TINKOFF
            )
        )
}
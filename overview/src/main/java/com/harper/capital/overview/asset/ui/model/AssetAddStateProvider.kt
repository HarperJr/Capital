package com.harper.capital.overview.asset.ui.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.harper.capital.spec.domain.AssetColor

class AssetAddStateProvider : PreviewParameterProvider<AssetAddState.Data> {
    override val values: Sequence<AssetAddState.Data>
        get() = sequenceOf(
            AssetAddState.Data(
                name = "",
                amount = 0.0,
                colors = AssetColor.values().toList(),
                selectedColor = AssetColor.DARK
            )
        )
}
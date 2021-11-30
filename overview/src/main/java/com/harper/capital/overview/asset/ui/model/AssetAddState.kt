package com.harper.capital.overview.asset.ui.model

import com.harper.capital.spec.domain.AssetColor

sealed class AssetAddState {

    object Loading : AssetAddState()

    data class Data(
        val name: String = "",
        val amount: Double = 0.0,
        val colors: List<AssetColor> = AssetColor.values().toList(),
        val selectedColor: AssetColor = AssetColor.DARK
    ) : AssetAddState()
}

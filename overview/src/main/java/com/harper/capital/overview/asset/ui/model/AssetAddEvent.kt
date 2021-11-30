package com.harper.capital.overview.asset.ui.model

import com.harper.capital.spec.domain.AssetColor

sealed class AssetAddEvent {

    data class ColorSelect(val color: AssetColor): AssetAddEvent()

    object Apply : AssetAddEvent()
}

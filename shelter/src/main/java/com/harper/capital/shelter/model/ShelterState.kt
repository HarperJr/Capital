package com.harper.capital.shelter.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.harper.capital.domain.model.Asset

data class ShelterState(
    val title: String = "Title",
    val assets: List<Asset> = emptyList()
) {
    var text: String by mutableStateOf("")
    var assetStates: List<AssetState> = assets.map { AssetState(it) }
}

class AssetState(val asset: Asset) {
    var amount: Double by mutableStateOf(asset.balance)
}

package com.harper.capital.overview.asset.model

sealed class AssetAddState {

    object Loading : AssetAddState()

    data class Data(val name: String = "") : AssetAddState()
}

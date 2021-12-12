package com.harper.capital.domain.model

sealed class AssetMetadata(val assetType: AssetType) {

    object Default : AssetMetadata(AssetType.DEFAULT)

    data class Credit(val limit: Double) : AssetMetadata(AssetType.CREDIT)

    data class Goal(val goal: Double) : AssetMetadata(AssetType.GOAL)
}

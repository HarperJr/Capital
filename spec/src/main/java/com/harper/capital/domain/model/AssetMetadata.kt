package com.harper.capital.domain.model

sealed class AssetMetadata(val assetType: AssetType) {

    object Debet : AssetMetadata(AssetType.DEBET)

    object Income : AssetMetadata(AssetType.INCOME)

    object Expense : AssetMetadata(AssetType.EXPENSE)

    data class Credit(val limit: Double) : AssetMetadata(AssetType.CREDIT)

    data class Goal(val goal: Double) : AssetMetadata(AssetType.GOAL)
}

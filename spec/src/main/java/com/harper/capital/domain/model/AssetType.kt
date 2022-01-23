package com.harper.capital.domain.model

enum class AssetType {
    DEBET,
    CREDIT,
    GOAL,
    INCOME,
    EXPENSE;

    companion object {

        fun assetValues(): List<AssetType> = listOf(DEBET, CREDIT, GOAL)
    }
}

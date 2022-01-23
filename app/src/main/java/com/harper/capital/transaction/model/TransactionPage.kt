package com.harper.capital.transaction.model

data class TransactionPage(
    val type: TransactionType,
    val assetDataSets: List<AssetDataSet>
)

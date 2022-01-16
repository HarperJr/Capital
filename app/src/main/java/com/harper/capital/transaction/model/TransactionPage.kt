package com.harper.capital.transaction.model

import androidx.annotation.StringRes

data class TransactionPage(
    @StringRes val titleRes: Int,
    val assetDataSets: List<AssetDataSet>
)

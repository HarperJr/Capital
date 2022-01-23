package com.harper.capital.transaction.model

import com.harper.capital.domain.model.Asset

data class AssetDataSet(
    val type: DataSetType,
    val section: DataSetSection,
    val assets: List<Asset>,
    val selectedAssetId: Long? = null
)

package com.harper.capital.spec.domain

data class Asset(
    val id: Long,
    val name: String,
    val amount: Double,
    val currency: Currency,
    val color: AssetColor,
    val icon: AssetIcon,
    val metadata: AssetMetadata
)

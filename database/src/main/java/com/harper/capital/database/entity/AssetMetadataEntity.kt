package com.harper.capital.database.entity

object AssetMetadataCode {
    const val default = 0
    const val credit = 2
    const val goal = 3
}

sealed class AssetMetadataEntity(val code: Int) {

    object Default : AssetMetadataEntity(code = AssetMetadataCode.default)

    data class Credit(val limit: Double) : AssetMetadataEntity(code = AssetMetadataCode.credit)

    data class Goal(val goal: Double) : AssetMetadataEntity(code = AssetMetadataCode.goal)
}

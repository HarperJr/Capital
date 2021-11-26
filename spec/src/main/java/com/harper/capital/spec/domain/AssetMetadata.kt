package com.harper.capital.spec.domain

sealed class AssetMetadata {

    object Default : AssetMetadata()

    data class Credit(val limit: Double) : AssetMetadata()

    data class Goal(val goal: Double) : AssetMetadata()
}

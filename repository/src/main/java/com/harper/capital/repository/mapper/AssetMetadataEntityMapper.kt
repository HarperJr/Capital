package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AssetMetadataEntity
import com.harper.capital.spec.domain.AssetMetadata

object AssetMetadataEntityMapper : (AssetMetadata) -> AssetMetadataEntity {

    override fun invoke(model: AssetMetadata): AssetMetadataEntity = when(model) {
        is AssetMetadata.Default -> AssetMetadataEntity.Default
        is AssetMetadata.Credit -> AssetMetadataEntity.Credit(model.limit)
        is AssetMetadata.Goal -> AssetMetadataEntity.Goal(model.goal)
    }
}

package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AssetMetadataEntity
import com.harper.capital.spec.domain.AssetMetadata

internal object AssetMetadataMapper : (AssetMetadataEntity) -> AssetMetadata {

    override fun invoke(entity: AssetMetadataEntity): AssetMetadata = when(entity) {
        is AssetMetadataEntity.Default -> AssetMetadata.Default
        is AssetMetadataEntity.Credit -> AssetMetadata.Credit(entity.limit)
        is AssetMetadataEntity.Goal -> AssetMetadata.Goal(entity.goal)
    }
}
